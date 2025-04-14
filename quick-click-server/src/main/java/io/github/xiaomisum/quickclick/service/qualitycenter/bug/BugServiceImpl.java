package io.github.xiaomisum.quickclick.service.qualitycenter.bug;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import io.github.xiaomisum.quickclick.controller.quality.bug.vo.BugQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Bug;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.BugExecRecord;
import io.github.xiaomisum.quickclick.dal.mapper.qualitycenter.BugMapper;
import io.github.xiaomisum.quickclick.dal.mapper.qualitycenter.BugRecordMapper;
import io.github.xiaomisum.quickclick.enums.BugStatus;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.security.core.util.SecurityFrameworkUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static io.github.xiaomisum.quickclick.enums.BugStatus.*;

@Service
public class BugServiceImpl implements BugService {

    @Resource
    private BugMapper mapper;
    @Resource
    private BugRecordMapper commentMapper;

    @Override
    public PageResult<Bug> getPage(BugQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public Bug get(String id) {
        return mapper.selectById(id);
    }

    @Override
    public String add(Bug data) {
        data.setId(IdUtil.getSnowflakeNextIdStr());
        mapper.insert(data);
        addRecord(new BugExecRecord().setBugId(data.getId())
                .setUserId(SecurityFrameworkUtils.getLoginUserId())
                .setOperation(New)
                .setContent(""));
        return data.getId();
    }

    @Override
    public void update(Bug data) {
        mapper.updateById(data);
    }

    @Override
    public void assign(List<String> ids, Long handler) {
        List<Bug> bugs = Lists.newArrayList();
        ids.forEach(id -> bugs.add((Bug) new Bug().setAssignedTime(LocalDateTime.now()).setHandler(handler).setId(id)));
        mapper.updateBatch(bugs);
    }

    @Override
    public void confirm(List<String> ids) {
        List<Bug> bugs = Lists.newArrayList();
        List<BugExecRecord> comments = Lists.newArrayList();
        ids.forEach(id -> {
            bugs.add((Bug) new Bug().setConfirmedTime(LocalDateTime.now()).setStatus(Opened).setId(id));
            comments.add(new BugExecRecord().setBugId(id).setUserId(SecurityFrameworkUtils.getLoginUserId()).setOperation(Opened).setContent(""));
        });
        mapper.updateBatch(bugs);
        commentMapper.insertBatch(comments);
    }

    @Override
    public void confirm(Bug data, String comment) {
        data.setStatus(Opened);
        data.setConfirmedTime(LocalDateTime.now());
        mapper.updateById(data);
        addRecord(new BugExecRecord().setBugId(data.getId())
                .setUserId(SecurityFrameworkUtils.getLoginUserId())
                .setOperation(Opened)
                .setContent(StrUtil.isBlank(comment) ? "" : comment));

    }

    @Override
    public void reject(Bug data) {
        data.setStatus(Rejected);
        data.setAssignedTime(LocalDateTime.now());
        data.setRejectedTime(LocalDateTime.now());
        mapper.updateById(data);
        addRecord(new BugExecRecord().setBugId(data.getId())
                .setUserId(SecurityFrameworkUtils.getLoginUserId())
                .setOperation(Rejected)
                .setContent(""));
    }

    @Override
    public void fix(Bug data) {
        data.setStatus(Fixed);
        data.setFixedTime(LocalDateTime.now());
        data.setFixer(SecurityFrameworkUtils.getLoginUserId());
        data.setAssignedTime(LocalDateTime.now());
        mapper.updateById(data);
        addRecord(new BugExecRecord().setBugId(data.getId())
                .setUserId(SecurityFrameworkUtils.getLoginUserId())
                .setOperation(Fixed)
                // 修复bug 需记录耗时
                .setDuration(data.getFixDuration())
                .setContent(""));
    }

    @Override
    public void reopen(String id, Long handler, String comment, Integer duration) {
        mapper.reopenById(id, Reopened, handler);
        addRecord(new BugExecRecord().setBugId(id)
                .setUserId(SecurityFrameworkUtils.getLoginUserId())
                .setOperation(Reopened)
                // todo 激活Bug 需要消耗测试人员工时，记录测试工时
                .setDuration(duration)
                .setContent(StrUtil.isBlank(comment) ? "" : comment));
    }

    @Override
    public void close(String id, String comment, Integer duration) {
        mapper.closeById(id, Closed, SecurityFrameworkUtils.getLoginUserId());
        addRecord(new BugExecRecord().setBugId(id)
                .setUserId(SecurityFrameworkUtils.getLoginUserId())
                .setOperation(Closed)
                // todo 关闭Bug 需要消耗测试人员工时，记录测试工时
                .setDuration(duration)
                .setContent(StrUtil.isBlank(comment) ? "" : comment));

    }

    @Override
    public void remove(String id) {
        mapper.deleteById(id);
    }

    @Override
    public List<BugExecRecord> getRecords(String bugId) {
        return commentMapper.selectList(bugId);
    }

    @Override
    public void addRecord(BugExecRecord data) {
        commentMapper.insert(data);
    }

    @Override
    public Long count(Long userId, BugStatus... status) {
        return mapper.selectCount(userId, status);
    }

    @Override
    public List<Bug> loadProjectBugByCreator(String projectId, Collection<Long> creator, LocalDateTime startTime, LocalDateTime endTime) {
        return mapper.selectListByCreator(projectId, creator, startTime, endTime);
    }

    @Override
    public List<Bug> loadProjectBugBySupervisor(String projectId, Collection<Long> supervisor,
                                                LocalDateTime startTime, LocalDateTime endTime) {
        return mapper.selectListBySupervisor(projectId, supervisor, startTime, endTime);
    }

    @Override
    public List<BugExecRecord> loadProjectClosedRecords(String projectId, Collection<Long> closer, LocalDateTime startTime, LocalDateTime endTime) {
        return commentMapper.selectClosedBug(projectId, closer, startTime, endTime);
    }

    @Override
    public List<BugExecRecord> loadProjectFixedRecords(String projectId, Collection<Long> fixer, LocalDateTime startTime, LocalDateTime endTime) {
        return commentMapper.selectRecordsByOperation(projectId, fixer, Fixed, startTime, endTime);
    }

    @Override
    public List<Bug> loadProjectReopenBug(String projectId, LocalDateTime startTime, LocalDateTime endTime) {
        return commentMapper.selectBugByReopen(projectId, startTime, endTime);
    }

    @Override
    public List<Bug> loadProjectCloseBug(String projectId, LocalDateTime startTime, LocalDateTime endTime) {
        return mapper.selectListByClose(projectId, startTime, endTime);
    }

    @Override
    public List<BugExecRecord> loadProjectReopenRecords(String projectId, List<Long> testers, LocalDateTime startTime, LocalDateTime endTime) {
        return commentMapper.selectRecordsByOperation(projectId, testers, Reopened, startTime, endTime);
    }
}
