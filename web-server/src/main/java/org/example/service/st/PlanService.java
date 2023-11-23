package org.example.service.st;

import org.example.controller.st.plan.vo.PlanQueryReqVO;
import org.example.dal.dataobject.st.Plan;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface PlanService {

    PageResult<Plan> getPage(PlanQueryReqVO req);

    List<Plan> getList(Long projectId);

    Plan get(Long projectId, Long id);

    Long add(Plan plan);

    void update(Plan plan);

    void remove(Long id);
}
