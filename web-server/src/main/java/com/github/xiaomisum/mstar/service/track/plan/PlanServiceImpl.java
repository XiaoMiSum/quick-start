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

package com.github.xiaomisum.mstar.service.track.plan;

import com.github.xiaomisum.mstar.controller.track.plan.vo.PlanQueryReqVO;
import com.github.xiaomisum.mstar.dal.dataobject.track.Plan;
import com.github.xiaomisum.mstar.dal.dataobject.track.PlanCase;
import com.github.xiaomisum.mstar.dal.mapper.track.PlanCaseMapper;
import com.github.xiaomisum.mstar.dal.mapper.track.PlanMapper;
import com.github.xiaomisum.mstar.enums.TestStatus;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.xiaomisum.mstar.enums.TestStatus.*;
import static com.github.xiaomisum.mstar.service.track.review.ReviewServiceImpl.CRON;

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
    public Plan get(String projectId, String id) {
        return mapper.selectOne(projectId, id);
    }

    @Override
    public String add(Plan plan) {
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
        mapper.updateById((Plan) new Plan().setActualEndTime(new Date()).setId(planId));
    }


    @Scheduled(cron = CRON)
    public void scheduleUpdateStatus() {
        mapper.selectByStatus(Prepare).forEach(review -> {
            List<PlanCase> cases = planCaseMapper.selectList(review.getId());
            Map<TestStatus, List<PlanCase>> group = cases.stream()
                    .collect(Collectors.groupingBy(PlanCase::getExecuteResult));
            List<PlanCase> prepareList = Prepare.get(group);
            List<PlanCase> passList = Pass.get(group);
            List<PlanCase> failureList = Failure.get(group);
            List<PlanCase> blockingList = Blocking.get(group);
            List<PlanCase> skipList = Skip.get(group);
            List<PlanCase> underwayList = Underway.get(group);
            if (prepareList.isEmpty() && underwayList.isEmpty()) {
                // 进行中和未开始的都为空，评审完成
                mapper.updateStatus(review.getId(), complete);
            } else if ((passList.size() + failureList.size() +
                    blockingList.size() + skipList.size() + underwayList.size()) < cases.size()) {
                // 成功数量 + 失败数量 + 阻塞数量 + 跳过数量 + 进行中数量 之和 小于 总数，评审进行中
                mapper.updateStatus(review.getId(), Underway);
            }
        });
    }
}
