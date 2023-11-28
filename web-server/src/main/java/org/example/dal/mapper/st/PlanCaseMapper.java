package org.example.dal.mapper.st;

import org.apache.ibatis.annotations.Mapper;
import org.example.controller.st.plan.vo.PlanCaseQueryReqVO;
import org.example.dal.dataobject.st.PlanCase;
import org.example.enums.ResultEnum;
import org.example.model.dto.Statistics;
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
                .eqIfPresent(PlanCase::getModuleId, req.getModuleId())
                .likeIfPresent(PlanCase::getName, req.getCaseName())
        );
    }


    default List<PlanCase> selectList(Long planId) {
        return selectList(new LambdaQueryWrapperX<PlanCase>().eq(PlanCase::getPlanId, planId));
    }

    default Statistics statistics(Long planId) {
        return selectJoinOne(Statistics.class, new MPJLambdaWrapperX<PlanCase>()
                .select("ifNull(count(id), 0) total",
                        "sum(case when execute_result = 'PASSED' then 1 else 0 end) passed",
                        "sum(case when execute_result = 'NOTSTARTED' then 1 else 0 end) unstarted",
                        "sum(case when execute_result = 'SKIPPED' then 1 else 0 end) skipped")
                .eq("plan_id", planId));
    }

    default List<PlanCase> selectList(Long planId, Long caseId) {
        return selectList(new LambdaQueryWrapperX<PlanCase>()
                .eq(PlanCase::getPlanId, planId)
                .eq(PlanCase::getCaseId, caseId)
                .orderByAsc(PlanCase::getCaseId)
        );
    }

    default List<PlanCase> selectListByLtId(Long planId, Long id) {
        return selectList(new LambdaQueryWrapperX<PlanCase>()
                .eq(PlanCase::getPlanId, planId)
                .lt(PlanCase::getId, id)
                .orderByAsc(PlanCase::getCaseId)
        );
    }

    default List<PlanCase> selectListByGtId(Long planId, Long id) {
        return selectList(new LambdaQueryWrapperX<PlanCase>()
                .eq(PlanCase::getPlanId, planId)
                .gt(PlanCase::getId, id)
                .orderByAsc(PlanCase::getCaseId)
        );
    }

    default List<PlanCase> selectList(Long planId, ResultEnum result) {
        return selectList(new LambdaQueryWrapperX<PlanCase>()
                .eq(PlanCase::getPlanId, planId)
                .eq(PlanCase::getExecuteResult, result)
        );
    }

}
