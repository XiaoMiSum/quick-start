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

package io.github.xiaomisum.quickclick.dal.mapper.track;

import io.github.xiaomisum.quickclick.controller.track.plan.vo.PlanCaseQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.track.PlanCase;
import io.github.xiaomisum.quickclick.enums.TestStatus;
import io.github.xiaomisum.quickclick.model.dto.Statistics;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;
import xyz.migoo.framework.mybatis.core.MPJLambdaWrapperX;

import java.util.List;

@Mapper
public interface PlanCaseMapper extends BaseMapperX<PlanCase> {

    default PageResult<PlanCase> selectPage(PlanCaseQueryReqVO req) {
        return selectPage(req, new LambdaQueryWrapperX<PlanCase>()
                .eq(PlanCase::getPlanId, req.getPlanId())
                .eqIfPresent(PlanCase::getNodeId, req.getNodeId())
                .likeIfPresent(PlanCase::getName, req.getCaseName())
        );
    }


    default List<PlanCase> selectList(String planId) {
        return selectList(new LambdaQueryWrapperX<PlanCase>().eq(PlanCase::getPlanId, planId));
    }

    default Statistics statistics(String planId) {
        return selectJoinOne(Statistics.class, new MPJLambdaWrapperX<PlanCase>()
                .select("ifNull(count(id), 0) total",
                        "sum(case when execute_result = 'Pass' then 1 else 0 end) passed",
                        "sum(case when execute_result = 'Prepare' then 1 else 0 end) unstarted",
                        "sum(case when execute_result = 'Skip' then 1 else 0 end) skipped")
                .eq("plan_id", planId));
    }

    default List<PlanCase> selectList(String planId, String caseId) {
        return selectList(new LambdaQueryWrapperX<PlanCase>()
                .eq(PlanCase::getPlanId, planId)
                .eq(PlanCase::getCaseId, caseId)
                .orderByAsc(PlanCase::getCaseId)
        );
    }

    default List<PlanCase> selectListByLtId(String planId, String id) {
        return selectList(new LambdaQueryWrapperX<PlanCase>()
                .eq(PlanCase::getPlanId, planId)
                .lt(PlanCase::getId, id)
                .orderByAsc(PlanCase::getCaseId)
        );
    }

    default List<PlanCase> selectListByGtId(String planId, String id) {
        return selectList(new LambdaQueryWrapperX<PlanCase>()
                .eq(PlanCase::getPlanId, planId)
                .gt(PlanCase::getId, id)
                .orderByAsc(PlanCase::getCaseId)
        );
    }

    default List<PlanCase> selectList(String planId, TestStatus result) {
        return selectList(new LambdaQueryWrapperX<PlanCase>()
                .eq(PlanCase::getPlanId, planId)
                .eq(PlanCase::getExecuteResult, result)
        );
    }


}
