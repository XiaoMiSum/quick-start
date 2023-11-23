package org.example.service.st;

import org.example.dal.dataobject.st.TestcaseHistory;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface TestcaseHistoryService {

    PageResult<TestcaseHistory> getPage(Long projectId, String version);

    List<TestcaseHistory> getList(Long projectId, String version);

    List<TestcaseHistory> getList(Long projectId);

    void add(TestcaseHistory testcase);

    void update(TestcaseHistory testcase);

    void remove(Long projectId, String version);
}
