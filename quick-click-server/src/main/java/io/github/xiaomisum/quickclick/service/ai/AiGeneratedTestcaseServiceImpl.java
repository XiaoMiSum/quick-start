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

package io.github.xiaomisum.quickclick.service.ai;

import cn.hutool.core.util.IdUtil;
import io.github.xiaomisum.quickclick.dal.dataobject.ai.AiGeneratedTestcase;
import io.github.xiaomisum.quickclick.dal.mapper.ai.AiGeneratedTestcaseMapper;
import io.github.xiaomisum.quickclick.enums.AiGeneratedTestcaseStatusEnum;
import io.github.xiaomisum.quickclick.dal.dataobject.project.Requirement;
import io.github.xiaomisum.quickclick.service.project.RequirementService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.exception.ServiceException;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.query.LambdaQueryWrapperX;
import xyz.migoo.framework.security.core.LoginUser;

import java.util.List;

@Service
public class AiGeneratedTestcaseServiceImpl implements AiGeneratedTestcaseService {

    @Resource
    private AiGeneratedTestcaseMapper testcaseMapper;

    @Resource
    private RequirementService requirementService;

    @Override
    public Long createTestcase(AiGeneratedTestcase testcase, LoginUser user) {
        // 验证需求是否存在
        Requirement requirement = requirementService.get(testcase.getRequirementId());
        if (requirement == null) {
            throw new ServiceException("需求不存在");
        }

        testcase.setId(IdUtil.getSnowflakeNextId());
        testcase.setCreator(user.getId());
        // 设置默认状态为待确认
        if (testcase.getStatus() == null) {
            testcase.setStatus(AiGeneratedTestcaseStatusEnum.PENDING.getStatus());
        }
        testcaseMapper.insert(testcase);
        return testcase.getId();
    }

    @Override
    public boolean updateTestcase(AiGeneratedTestcase testcase, LoginUser user) {
        // 验证需求是否存在
        Requirement requirement = requirementService.get(testcase.getRequirementId());
        if (requirement == null) {
            throw new ServiceException("需求不存在");
        }

        testcase.setCreator(user.getId());
        return testcaseMapper.updateById(testcase) > 0;
    }

    @Override
    public boolean deleteTestcase(Long id, LoginUser user) {
        return testcaseMapper.deleteById(id) > 0;
    }

    @Override
    public AiGeneratedTestcase getTestcase(Long id) {
        return testcaseMapper.selectById(id);
    }

    @Override
    public List<AiGeneratedTestcase> getTestcaseList(String requirementId, Integer status) {
        return testcaseMapper.selectList(new LambdaQueryWrapperX<AiGeneratedTestcase>()
                .eqIfPresent(AiGeneratedTestcase::getRequirementId, requirementId)
                .eqIfPresent(AiGeneratedTestcase::getStatus, status)
                .orderByDesc(AiGeneratedTestcase::getCreateTime));
    }

    @Override
    public PageResult<AiGeneratedTestcase> getTestcasePage(String requirementId, Integer status, Integer pageNo,
            Integer pageSize) {
        return testcaseMapper.selectPage(new LambdaQueryWrapperX<AiGeneratedTestcase>()
                .eqIfPresent(AiGeneratedTestcase::getRequirementId, requirementId)
                .eqIfPresent(AiGeneratedTestcase::getStatus, status)
                .orderByDesc(AiGeneratedTestcase::getCreateTime), pageNo, pageSize);
    }

    @Override
    public boolean confirmTestcase(Long id, LoginUser user) {
        AiGeneratedTestcase testcase = testcaseMapper.selectById(id);
        if (testcase == null) {
            throw new ServiceException("测试用例不存在");
        }

        // 只有待确认状态的测试用例才能被确认
        if (!AiGeneratedTestcaseStatusEnum.PENDING.getStatus().equals(testcase.getStatus())) {
            throw new ServiceException("只有待确认状态的测试用例才能被确认");
        }

        testcase.setStatus(AiGeneratedTestcaseStatusEnum.CONFIRMED.getStatus());
        return testcaseMapper.updateById(testcase) > 0;
    }

    @Override
    public boolean rejectTestcase(Long id, LoginUser user) {
        AiGeneratedTestcase testcase = testcaseMapper.selectById(id);
        if (testcase == null) {
            throw new ServiceException("测试用例不存在");
        }

        // 只有待确认状态的测试用例才能被拒绝
        if (!AiGeneratedTestcaseStatusEnum.PENDING.getStatus().equals(testcase.getStatus())) {
            throw new ServiceException("只有待确认状态的测试用例才能被拒绝");
        }

        testcase.setStatus(AiGeneratedTestcaseStatusEnum.REJECTED.getStatus());
        return testcaseMapper.updateById(testcase) > 0;
    }

    @Override
    public boolean importTestcases(List<Long> ids, LoginUser user) {
        if (ids == null || ids.isEmpty()) {
            throw new ServiceException("请选择要导入的测试用例");
        }

        // 批量更新状态为已导入
        for (Long id : ids) {
            AiGeneratedTestcase testcase = testcaseMapper.selectById(id);
            if (testcase == null) {
                throw new ServiceException("测试用例不存在，ID: " + id);
            }

            // 只有已确认状态的测试用例才能被导入
            if (!AiGeneratedTestcaseStatusEnum.CONFIRMED.getStatus().equals(testcase.getStatus())) {
                throw new ServiceException("只有已确认状态的测试用例才能被导入，ID: " + id);
            }

            // 更新状态为已导入
            testcase.setStatus(AiGeneratedTestcaseStatusEnum.IMPORTED.getStatus());
            testcaseMapper.updateById(testcase);
        }

        // 模拟导入成功
        return true;
    }
}