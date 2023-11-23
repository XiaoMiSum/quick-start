package org.example.service.st;

import org.example.controller.st.testcase.vo.TestcaseQueryReqVO;
import org.example.dal.dataobject.st.Testcase;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface TestcaseService {

    PageResult<Testcase> getPage(TestcaseQueryReqVO req);

    List<Testcase> getList(Long projectId);

    PageResult<Testcase> getPage(TestcaseQueryReqVO req, List<Long> notInIds);

    Testcase get(Long id);

    List<Testcase> getList(List<Long> ids);

    void add(Testcase testcase);

    void update(Testcase testcase);

    void remove(Long id);

    void remove(List<Long> ids);

}
