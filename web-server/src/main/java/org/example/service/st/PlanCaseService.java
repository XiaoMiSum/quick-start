package org.example.service.st;

import org.example.controller.st.plan.vo.PlanCaseExecuteVO;
import org.example.controller.st.plan.vo.PlanCaseQueryReqVO;
import org.example.dal.dataobject.st.PlanCase;
import org.example.enums.ResultEnum;
import org.example.model.dto.Statistics;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface PlanCaseService {

    PageResult<PlanCase> getPage(PlanCaseQueryReqVO req);

    PlanCase get(Long id);

    List<PlanCase> getList(Long planId);

    List<PlanCase> getList(Long planId, ResultEnum result);

    List<PlanCase> getListGtId(String opt, Long planId, Long id);

    void add(List<PlanCase> cases);

    void update(PlanCase planCase);

    void remove(Long id);

    void remove(List<Long> ids);

    Statistics statistics(Long planId);

    void execute(PlanCaseExecuteVO execute);

}
