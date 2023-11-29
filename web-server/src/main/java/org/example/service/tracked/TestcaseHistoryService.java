package org.example.service.tracked;

import org.example.dal.dataobject.tracked.TestcaseHistory;
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
