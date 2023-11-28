package org.example.service.st;

import cn.hutool.core.collection.CollectionUtil;
import jakarta.annotation.Resource;
import org.example.controller.st.plan.vo.PlanCaseExecuteVO;
import org.example.controller.st.plan.vo.PlanCaseQueryReqVO;
import org.example.dal.dataobject.st.PlanCase;
import org.example.dal.mapper.st.PlanCaseMapper;
import org.example.enums.ResultEnum;
import org.example.model.dto.Statistics;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class PlanCaseServiceImpl implements PlanCaseService {

    @Resource
    private PlanCaseMapper mapper;

    @Override
    public PageResult<PlanCase> getPage(PlanCaseQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public PlanCase get(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<PlanCase> getList(Long planId) {
        return mapper.selectList(planId);
    }

    @Override
    public List<PlanCase> getList(Long planId, ResultEnum result) {
        return mapper.selectList(planId, result);
    }

    @Override
    public List<PlanCase> getListGtId(String opt, Long planId, Long id) {
        return (Objects.equals(opt, "next")) ?
                mapper.selectListByGtId(planId, id) : mapper.selectListByLtId(planId, id);
    }

    @Override
    public void add(List<PlanCase> cases) {
        // 过滤已添加的
        mapper.insertBatch(cases.stream()
                .filter(item -> CollectionUtil.isEmpty(mapper.selectList(item.getPlanId(), item.getCaseId())))
                .toList());
    }

    @Override
    public void update(PlanCase planCase) {
        mapper.updateById(planCase);
    }

    @Override
    public void remove(Long id) {
        mapper.deleteById(id);
    }

    @Override
    public void remove(List<Long> ids) {
        mapper.deleteBatchIds(ids);
    }

    @Override
    public Statistics statistics(Long planId) {
        return mapper.statistics(planId);
    }

    @Override
    public void execute(PlanCaseExecuteVO execute) {
        PlanCase planCase = new PlanCase()
                .setId(execute.getId())
                .setSteps(execute.getSteps())
                .setExecutor(execute.getExecutor())
                .setExecuteTime(new Date())
                .setExecuteResult(execute.getResult());
        mapper.updateById(planCase);
    }
}
