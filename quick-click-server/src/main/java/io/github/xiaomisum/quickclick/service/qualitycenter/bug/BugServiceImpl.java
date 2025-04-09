package io.github.xiaomisum.quickclick.service.qualitycenter.bug;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import io.github.xiaomisum.quickclick.controller.quality.bug.vo.BugQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Bug;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.BugComment;
import io.github.xiaomisum.quickclick.dal.mapper.qualitycenter.BugCommentMapper;
import io.github.xiaomisum.quickclick.dal.mapper.qualitycenter.BugMapper;
import io.github.xiaomisum.quickclick.enums.BugStatus;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.security.core.util.SecurityFrameworkUtils;

import java.time.LocalDateTime;
import java.util.List;

import static io.github.xiaomisum.quickclick.enums.BugStatus.*;

@Service
public class BugServiceImpl implements BugService {

    @Resource
    private BugMapper mapper;
    @Resource
    private BugCommentMapper commentMapper;

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
        ids.forEach(id -> bugs.add((Bug) new Bug().setConfirmedTime(LocalDateTime.now()).setStatus(Opened).setId(id)));
        mapper.updateBatch(bugs);
    }

    @Override
    public void confirm(Bug data, String comment) {
        data.setStatus(Opened);
        data.setConfirmedTime(LocalDateTime.now());
        mapper.updateById(data);
        if (StrUtil.isNotBlank(comment)) {
            addComment(new BugComment().setBugId(data.getId())
                    .setUserId(SecurityFrameworkUtils.getLoginUserId())
                    .setOperation(Opened)
                    .setContent(comment));
        }
    }

    @Override
    public void reject(Bug data) {
        data.setStatus(Rejected);
        data.setAssignedTime(LocalDateTime.now());
        data.setRejectedTime(LocalDateTime.now());
        mapper.updateById(data);
    }

    @Override
    public void fix(Bug data) {
        data.setStatus(Fixed);
        data.setFixedTime(LocalDateTime.now());
        data.setFixer(SecurityFrameworkUtils.getLoginUserId());
        data.setAssignedTime(LocalDateTime.now());
        mapper.updateById(data);
    }

    @Override
    public void reopen(String id, Long handler, String comment) {
        mapper.reopenById(id, Reopened, handler);
        addComment(new BugComment().setBugId(id)
                .setUserId(SecurityFrameworkUtils.getLoginUserId())
                .setOperation(Reopened)
                .setContent(StrUtil.isBlank(comment) ? "" : comment));
    }

    @Override
    public void close(String id, String comment) {
        mapper.closeById(id, Closed, SecurityFrameworkUtils.getLoginUserId());
        if (StrUtil.isNotBlank(comment)) {
            addComment(new BugComment().setBugId(id)
                    .setUserId(SecurityFrameworkUtils.getLoginUserId())
                    .setOperation(Closed)
                    .setContent(comment));
        }
    }

    @Override
    public void remove(String id) {
        mapper.deleteById(id);
    }

    @Override
    public List<BugComment> getComment(String bugId) {
        return commentMapper.selectList(bugId);
    }

    @Override
    public void addComment(BugComment data) {
        commentMapper.insert(data);
    }

    @Override
    public void removeComment(Long id) {
        commentMapper.deleteById(id);
    }

    @Override
    public Long count(Long userId, BugStatus... status) {
        return mapper.selectCount(userId, status);
    }

}
