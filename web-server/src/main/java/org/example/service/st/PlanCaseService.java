package org.example.service.st;

import org.example.controller.st.plan.vo.PlanCaseQueryReqVO;
import org.example.dal.dataobject.st.PlanCase;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface PlanCaseService {

    PageResult<PlanCase> getPage(PlanCaseQueryReqVO req);

    List<PlanCase> getList(Long planId);

    void add(List<PlanCase> cases);

    void update(PlanCase planCase);

    void remove(Long id);
}
