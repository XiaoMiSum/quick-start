package org.example.dal.mapper.st;

import org.apache.ibatis.annotations.Mapper;
import org.example.controller.st.plan.vo.PlanQueryReqVO;
import org.example.dal.dataobject.st.Plan;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

import java.util.List;

@Mapper
public interface PlanMapper extends BaseMapperX<Plan> {

    default PageResult<Plan> selectPage(PlanQueryReqVO req) {
        return selectPage(req, new LambdaQueryWrapperX<Plan>()
                .eq(Plan::getProjectId, req.getProjectId())
                .likeIfPresent(Plan::getName, req.getName())
        );
    }

    default List<Plan> selectList(Long projectId) {
        return selectList(new LambdaQueryWrapperX<Plan>().eq(Plan::getProjectId, projectId));
    }

    default Plan selectOne(Long projectId, Long id) {
        return selectOne(new LambdaQueryWrapperX<Plan>()
                .eq(Plan::getId, id)
                .eq(Plan::getProjectId, projectId));
    }
}
