package io.github.xiaomisum.quickclick.service.qualitycenter.bug;

import io.github.xiaomisum.quickclick.controller.quality.bug.vo.BugQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Bug;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface BugService {

    PageResult<Bug> getPage(BugQueryReqVO req);

    Bug get(String id);

    String add(Bug data);

    void update(Bug data);

    void assign(List<String> ids, Long handler);

    void confirm(List<String> ids);

    void fix(Bug data);

    void reopen(String id);

    void close(String id);

    void remove(String id);
}
