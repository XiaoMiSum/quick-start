package org.example.service.tracked;

import jakarta.annotation.Resource;
import org.example.controller.tracked.testcase.vo.TestcaseQueryReqVO;
import org.example.dal.dataobject.tracked.Testcase;
import org.example.dal.mapper.tracked.TestcaseMapper;
import org.example.service.project.module.ModuleService;
import org.example.service.sys.user.UserService;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Service
public class TestcaseServiceImpl implements TestcaseService {

    @Resource
    private TestcaseMapper mapper;
    @Resource
    private ModuleService moduleService;
    @Resource
    private UserService userService;

    @Override
    public PageResult<Testcase> getPage(TestcaseQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public List<Testcase> getList(Long projectId) {
        return mapper.selectList(projectId);
    }

    @Override
    public PageResult<Testcase> getPage(TestcaseQueryReqVO req, List<Long> notInIds) {
        return mapper.selectPage(req, notInIds);
    }

    @Override
    public Testcase get(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Testcase> getList(List<Long> ids) {
        return mapper.selectBatchIds(ids);
    }

    @Override
    public Long add(Testcase testcase) {
        mapper.insert(testcase);
        return testcase.getId();
    }

    @Override
    public void add(List<Testcase> testcases) {
        mapper.insertBatch(testcases);
    }

    @Override
    public void update(Testcase testcase) {
        mapper.updateById(testcase);
    }

    @Override
    public void remove(Long id) {
        mapper.deleteById(id);
    }

    @Override
    public void remove(List<Long> ids) {
        mapper.deleteBatchIds(ids);
    }

}
