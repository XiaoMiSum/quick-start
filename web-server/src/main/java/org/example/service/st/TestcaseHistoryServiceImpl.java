package org.example.service.st;

import jakarta.annotation.Resource;
import org.example.dal.dataobject.st.TestcaseHistory;
import org.example.dal.mapper.st.TestcaseHistoryMapper;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Service
public class TestcaseHistoryServiceImpl implements TestcaseHistoryService {

    @Resource
    private TestcaseHistoryMapper mapper;

    @Override
    public PageResult<TestcaseHistory> getPage(Long projectId, String version) {
        return mapper.selectPage(projectId, version);
    }

    @Override
    public List<TestcaseHistory> getList(Long projectId, String version) {
        return mapper.selectList(projectId, version);
    }

    @Override
    public List<TestcaseHistory> getList(Long projectId) {
        return mapper.selectList(projectId);
    }

    @Override
    public void add(TestcaseHistory testcase) {
        mapper.insert(testcase);
    }

    @Override
    public void update(TestcaseHistory testcase) {
        mapper.updateById(testcase);
    }

    @Override
    public void remove(Long projectId, String version) {
        mapper.deleteByVersion(projectId, version);
    }
}
