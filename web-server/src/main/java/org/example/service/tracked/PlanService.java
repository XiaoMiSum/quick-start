package org.example.service.tracked;

import org.example.controller.tracked.plan.vo.PlanQueryReqVO;
import org.example.dal.dataobject.tracked.Plan;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface PlanService {

    PageResult<Plan> getPage(PlanQueryReqVO req);

    List<Plan> getList(Long projectId);

    Plan get(Long projectId, Long id);

    Long add(Plan plan);

    void update(Plan plan);

    void remove(Long id);

    void setStartTime(Long planId);

    void setEndTime(Long planId);
}
