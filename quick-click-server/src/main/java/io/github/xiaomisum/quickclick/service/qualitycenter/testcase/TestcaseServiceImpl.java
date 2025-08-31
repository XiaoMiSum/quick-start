/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021.  Lorem XiaoMiSum (mi_xiao@qq.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * 'Software'), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.github.xiaomisum.quickclick.service.qualitycenter.testcase;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import io.github.xiaomisum.quickclick.controller.quality.testcase.vo.TestcaseQueryReqVO;
import io.github.xiaomisum.quickclick.convert.qualitycenter.TestcaseConvert;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Testcase;
import io.github.xiaomisum.quickclick.dal.mapper.qualitycenter.TestcaseMapper;
import io.github.xiaomisum.quickclick.model.dto.TestcasePageDTO;
import io.github.xiaomisum.quickclick.service.project.NodeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TestcaseServiceImpl implements TestcaseService {

    @Resource
    private TestcaseMapper mapper;
    @Resource
    private NodeService node;

    @Override
    public PageResult<Testcase> getPage(TestcaseQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public List<Testcase> loadIfUpdate(LocalDateTime maxUpdateTime, Integer offset) {
        // 如果更新时间为空，则获取最近指定天数的所有数据
        maxUpdateTime = Objects.isNull(maxUpdateTime) ? LocalDateTime.now().minusDays(offset) : maxUpdateTime;
        return mapper.selectByUpdateTimeAfter(maxUpdateTime);
    }

    @Override
    public List<Testcase> getList(String projectId) {
        return mapper.selectList(projectId);
    }

    @Override
    public PageResult<TestcasePageDTO> getPage(TestcaseQueryReqVO req, List<String> notInIds) {
        return TestcaseConvert.INSTANCE.convert1(mapper.selectPage(req, notInIds));
    }

    @Override
    public Testcase get(String id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Testcase> getList(List<String> ids) {
        return mapper.selectBatchIds(ids);
    }

    @Override
    public String add(Testcase testcase) {
        testcase.setPath(node.get(testcase.getNodeId()).getPath()).setId(IdUtil.getSnowflakeNextIdStr());
        mapper.insert(testcase);
        return testcase.getId();
    }

    @Override
    public void add(List<Testcase> testcases) {
        testcases.forEach(
                item -> item.setPath(node.get(item.getNodeId()).getPath()).setId(IdUtil.getSnowflakeNextIdStr()));
        mapper.insertBatch(testcases);
    }

    @Override
    public void update(Testcase testcase) {
        if (StrUtil.isNotBlank(testcase.getNodeId())) {
            testcase.setPath(node.get(testcase.getNodeId()).getPath());
        }
        mapper.updateById(testcase);
    }

    @Override
    public void update(List<Testcase> data) {
        data.forEach(this::update);
    }

    @Override
    public void moveToTrash(List<String> ids, String projectId) {
        mapper.moveToTrash(ids, projectId);
    }

    @Override
    public void recover(List<String> ids, String projectId) {
        mapper.recover(ids, projectId);
    }

    @Override
    public void removeTrash(List<String> ids, String projectId) {
        mapper.clear(ids, projectId);
    }

    @Override
    public List<Testcase> loadTestCase(LocalDateTime startTime, LocalDateTime endTime) {
        return mapper.selectList(startTime, endTime);
    }

    @Override
    public String createNewVersion(Testcase testcase) {
        // 获取原用例
        Testcase original = mapper.selectById(testcase.getParentId());
        if (original == null) {
            throw ServiceExceptionUtil.get(ErrorCodeConstants.ORIGINAL_CASE_NOT_EXIST);
        }

        // 转换为版本用例
        Testcase versionTestcase = TestcaseConvert.INSTANCE.convertToVersion(original, testcase);
        versionTestcase.setId(IdUtil.getSnowflakeNextIdStr());
        versionTestcase.setPath(node.get(versionTestcase.getNodeId()).getPath());

        // 保存新版本
        mapper.insert(versionTestcase);
        return versionTestcase.getId();
    }

    @Override
    public List<Testcase> getVersionList(String parentId) {
        return mapper.selectList(new LambdaQueryWrapperX<Testcase>()
                .and(wrapper -> wrapper.eq(Testcase::getId, parentId)
                        .or()
                        .eq(Testcase::getParentId, parentId))
                .orderByAsc(Testcase::getVersion));
    }

    @Override
    public String compareVersions(String version1Id, String version2Id) {
        Testcase version1 = mapper.selectById(version1Id);
        Testcase version2 = mapper.selectById(version2Id);

        if (version1 == null || version2 == null) {
            throw new RuntimeException("用例版本不存在");
        }

        // 构建详细的对比结果
        StringBuilder diff = new StringBuilder();

        // 标题对比
        if (!version1.getTitle().equals(version2.getTitle())) {
            diff.append("标题不同: ").append(version1.getTitle()).append(" -> ").append(version2.getTitle()).append("\n");
        }

        // 优先级对比
        if (!version1.getPriority().equals(version2.getPriority())) {
            diff.append("优先级不同: ").append(version1.getPriority()).append(" -> ").append(version2.getPriority())
                    .append("\n");
        }

        // 前置条件对比
        if (!Objects.equals(version1.getPrerequisite(), version2.getPrerequisite())) {
            diff.append("前置条件不同: ").append(version1.getPrerequisite()).append(" -> ").append(version2.getPrerequisite())
                    .append("\n");
        }

        // 标签对比
        if (!Objects.equals(version1.getTags(), version2.getTags())) {
            diff.append("标签不同: ").append(version1.getTags()).append(" -> ").append(version2.getTags())
                    .append("\n");
        }

        // 前端开发人员对比
        if (!Objects.equals(version1.getFrontendDeveloper(), version2.getFrontendDeveloper())) {
            diff.append("前端开发不同: ").append(version1.getFrontendDeveloper()).append(" -> ")
                    .append(version2.getFrontendDeveloper())
                    .append("\n");
        }

        // 后端开发人员对比
        if (!Objects.equals(version1.getBackendDeveloper(), version2.getBackendDeveloper())) {
            diff.append("后端开发不同: ").append(version1.getBackendDeveloper()).append(" -> ")
                    .append(version2.getBackendDeveloper())
                    .append("\n");
        }

        // 责任人对比
        if (!Objects.equals(version1.getSupervisor(), version2.getSupervisor())) {
            diff.append("责任人不同: ").append(version1.getSupervisor()).append(" -> ").append(version2.getSupervisor())
                    .append("\n");
        }

        // 步骤对比
        if (!Objects.equals(version1.getSteps(), version2.getSteps())) {
            diff.append("执行步骤不同\n");
            // 可以进一步详细对比步骤差异
        }

        return diff.toString();
    }

}
