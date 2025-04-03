package io.github.xiaomisum.quickclick.service.qualitycenter.bug;

import cn.hutool.core.util.IdUtil;
import com.google.common.collect.Lists;
import io.github.xiaomisum.quickclick.controller.quality.bug.vo.BugQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Bug;
import io.github.xiaomisum.quickclick.dal.mapper.qualitycenter.BugMapper;
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
    public void confirm(Bug data) {
        data.setStatus(Opened);
        data.setConfirmedTime(LocalDateTime.now());
        mapper.updateById(data);
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
    public void reopen(String id, Long handler) {
        mapper.reopenById(id, Reopened, handler);
    }

    @Override
    public void close(String id) {
        mapper.closeById(id, Closed, SecurityFrameworkUtils.getLoginUserId());
    }

    @Override
    public void remove(String id) {
        mapper.deleteById(id);
    }

}
