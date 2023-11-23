package org.example.service.st;

import jakarta.annotation.Resource;
import org.example.controller.st.plan.vo.PlanQueryReqVO;
import org.example.dal.dataobject.st.Plan;
import org.example.dal.mapper.st.PlanMapper;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {

    @Resource
    private PlanMapper mapper;

    @Override
    public PageResult<Plan> getPage(PlanQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public List<Plan> getList(Long projectId) {
        return mapper.selectList(projectId);
    }

    @Override
    public Plan get(Long projectId, Long id) {
        return mapper.selectOne(projectId, id);
    }

    @Override
    public Long add(Plan plan) {
        mapper.insert(plan);
        return plan.getId();
    }

    @Override
    public void update(Plan plan) {

    }

    @Override
    public void remove(Long id) {

    }
}
