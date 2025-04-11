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
        testcases.forEach(item -> item.setPath(node.get(item.getNodeId()).getPath()).setId(IdUtil.getSnowflakeNextIdStr()));
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


}
