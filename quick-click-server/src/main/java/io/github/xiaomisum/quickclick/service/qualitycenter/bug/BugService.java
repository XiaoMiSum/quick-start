package io.github.xiaomisum.quickclick.service.qualitycenter.bug;

import io.github.xiaomisum.quickclick.controller.quality.bug.vo.BugQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Bug;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.BugExecRecord;
import io.github.xiaomisum.quickclick.enums.BugStatus;
import xyz.migoo.framework.common.pojo.PageResult;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface BugService {

    PageResult<Bug> getPage(BugQueryReqVO req);

    Bug get(String id);

    List<Bug> get(List<String> ids);

    String add(Bug data);

    void update(Bug data);

    void assign(List<String> ids, Long handler);

    void confirm(List<String> ids);

    void confirm(Bug data, String comment);

    void reject(Bug data);

    void fix(Bug data);

    void reopen(String id, Long handler, String comment, Integer duration);

    void close(String id, String comment, Integer duration);

    void remove(String id);

    List<BugExecRecord> getRecords(String bugId);

    void addRecord(BugExecRecord data);

    Long count(Long id, BugStatus... status);

    List<Bug> loadProjectBugByCreator(String projectId, Collection<Long> creator, LocalDateTime startTime, LocalDateTime endTime);

    List<Bug> loadProjectBugBySupervisor(String projectId, Collection<Long> Supervisor, LocalDateTime startTime, LocalDateTime endTime);

    List<BugExecRecord> loadProjectClosedRecords(String projectId, Collection<Long> closer, LocalDateTime startTime, LocalDateTime endTime);

    List<BugExecRecord> loadProjectFixedRecords(String projectId, Collection<Long> fixer, LocalDateTime startTime, LocalDateTime endTime);

    List<Bug> loadProjectReopenBug(String projectId, LocalDateTime startTime, LocalDateTime endTime);

    List<Bug> loadProjectCloseBug(String projectId, LocalDateTime startTime, LocalDateTime endTime);

    List<Bug> selectUncloseList(LocalDateTime startTime, LocalDateTime endTime);

    List<BugExecRecord> loadProjectReopenRecords(String projectId, List<Long> testers, LocalDateTime startTime, LocalDateTime endTime);
}
