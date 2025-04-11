package io.github.xiaomisum.quickclick.service.qualitycenter.bug;

import io.github.xiaomisum.quickclick.controller.quality.bug.vo.BugQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Bug;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.BugComment;
import io.github.xiaomisum.quickclick.enums.BugStatus;
import xyz.migoo.framework.common.pojo.PageResult;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface BugService {

    PageResult<Bug> getPage(BugQueryReqVO req);

    Bug get(String id);

    String add(Bug data);

    void update(Bug data);

    void assign(List<String> ids, Long handler);

    void confirm(List<String> ids);

    void confirm(Bug data, String comment);

    void reject(Bug data);

    void fix(Bug data);

    void reopen(String id, Long handler, String comment);

    void close(String id, String comment);

    void remove(String id);

    List<BugComment> getComment(String bugId);

    void addComment(BugComment data);

    void removeComment(Long id);

    Long count(Long id, BugStatus... status);

    List<Bug> loadProjectBugBySupervisor(String projectId, Collection<Long> Supervisor, LocalDateTime startTime, LocalDateTime endTime);

    List<Bug> loadProjectBugByFixer(String projectId, Collection<Long> fixer, LocalDateTime startTime, LocalDateTime endTime);

    List<Bug> loadProjectReopenBug(String projectId, LocalDateTime startTime, LocalDateTime endTime);

    List<Bug> loadProjectCloseBug(String projectId, LocalDateTime startTime, LocalDateTime endTime);
}
