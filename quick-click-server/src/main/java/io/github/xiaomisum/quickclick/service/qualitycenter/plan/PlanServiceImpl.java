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

package io.github.xiaomisum.quickclick.service.qualitycenter.plan;

import cn.hutool.core.util.IdUtil;
import io.github.xiaomisum.quickclick.controller.quality.plan.vo.PlanQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Plan;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.PlanCase;
import io.github.xiaomisum.quickclick.dal.mapper.qualitycenter.PlanCaseMapper;
import io.github.xiaomisum.quickclick.dal.mapper.qualitycenter.PlanMapper;
import io.github.xiaomisum.quickclick.enums.TestStatus;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.github.xiaomisum.quickclick.enums.TestStatus.*;
import static io.github.xiaomisum.quickclick.service.qualitycenter.review.ReviewServiceImpl.CRON;

@Service
public class PlanServiceImpl implements PlanService {

    @Resource
    private PlanMapper mapper;
    @Resource
    private PlanCaseMapper planCaseMapper;

    @Override
    public PageResult<Plan> getPage(PlanQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public List<Plan> getList(String projectId) {
        return mapper.selectList(projectId);
    }

    @Override
    public Plan get(String planId) {
        return mapper.selectById(planId);
    }

    @Override
    public String add(Plan plan) {
        plan.setId(IdUtil.getSnowflakeNextIdStr());
        mapper.insert(plan);
        return plan.getId();
    }

    @Override
    public void update(Plan plan) {
        mapper.updateById(plan);
    }

    @Override
    public void remove(String id) {
        mapper.deleteById(id);
    }

    @Override
    public void setStartTime(String planId) {
        mapper.updateStartTime(planId);
    }

    @Override
    public void setEndTime(String planId) {
        mapper.updateById((Plan) new Plan().setActualEndTime(LocalDateTime.now()).setId(planId));
    }


    @Scheduled(cron = CRON)
    public void scheduleUpdateStatus() {
        mapper.selectByStatus(Prepare).forEach(plan -> {
            List<PlanCase> cases = planCaseMapper.selectList(plan.getId());
            Map<TestStatus, List<PlanCase>> group = cases.stream()
                    .collect(Collectors.groupingBy(PlanCase::getResult));
            List<PlanCase> prepareList = Prepare.get(group);
            List<PlanCase> passList = Pass.get(group);
            List<PlanCase> failureList = Failure.get(group);
            List<PlanCase> blockingList = Blocking.get(group);
            List<PlanCase> skipList = Skip.get(group);
            List<PlanCase> underwayList = Underway.get(group);
            if (prepareList.isEmpty() && underwayList.isEmpty()) {
                // 进行中和未开始的都为空，评审完成
                mapper.updateStatus(plan.getId(), complete);
            } else if ((passList.size() + failureList.size() +
                    blockingList.size() + skipList.size() + underwayList.size()) < cases.size()) {
                // 成功数量 + 失败数量 + 阻塞数量 + 跳过数量 + 进行中数量 之和 小于 总数，评审进行中
                mapper.updateStatus(plan.getId(), Underway);
            }
        });
    }
}
