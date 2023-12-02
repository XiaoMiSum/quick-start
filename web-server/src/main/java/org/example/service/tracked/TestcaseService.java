package org.example.service.tracked;

import org.example.controller.tracked.testcase.vo.TestcaseQueryReqVO;
import org.example.dal.dataobject.tracked.Testcase;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface TestcaseService {

    PageResult<Testcase> getPage(TestcaseQueryReqVO req);

    List<Testcase> getList(Long projectId);

    PageResult<Testcase> getPage(TestcaseQueryReqVO req, List<Long> notInIds);

    Testcase get(Long id);

    List<Testcase> getList(List<Long> ids);

    Long add(Testcase testcase);

    void add(List<Testcase> testcases);

    void update(Testcase testcase);

    void remove(Long id);

    void remove(List<Long> ids);

}
