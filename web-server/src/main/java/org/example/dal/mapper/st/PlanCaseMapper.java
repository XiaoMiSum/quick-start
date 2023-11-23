package org.example.dal.mapper.st;

import org.apache.ibatis.annotations.Mapper;
import org.example.controller.st.plan.vo.PlanCaseQueryReqVO;
import org.example.dal.dataobject.st.PlanCase;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

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
}
