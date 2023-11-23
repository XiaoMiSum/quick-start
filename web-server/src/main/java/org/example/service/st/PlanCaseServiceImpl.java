package org.example.service.st;

import jakarta.annotation.Resource;
import org.example.controller.st.plan.vo.PlanCaseQueryReqVO;
import org.example.dal.dataobject.st.PlanCase;
import org.example.dal.mapper.st.PlanCaseMapper;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Service
public class PlanCaseServiceImpl implements PlanCaseService {

    @Resource
    private PlanCaseMapper mapper;

    @Override
    public PageResult<PlanCase> getPage(PlanCaseQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public List<PlanCase> getList(Long planId) {
        return mapper.selectList(planId);
    }

    @Override
    public void add(List<PlanCase> cases) {
        mapper.insertBatch(cases);
    }

    @Override
    public void update(PlanCase planCase) {
        mapper.updateById(planCase);
    }

    @Override
    public void remove(Long id) {
        mapper.deleteById(id);
    }
}
