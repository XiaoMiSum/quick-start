package io.github.xiaomisum.quickclick.service.qualitycenter.bug;

import cn.hutool.core.util.IdUtil;
import com.google.common.collect.Lists;
import io.github.xiaomisum.quickclick.controller.quality.bug.vo.BugQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Bug;
import io.github.xiaomisum.quickclick.dal.mapper.qualitycenter.BugMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

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
        ids.forEach(id -> bugs.add((Bug) new Bug().setAssignedTime(LocalDateTime.now()).setStatus(Opened).setId(id)));
        mapper.updateBatch(bugs);
    }

    @Override
    public void fix(Bug data) {
        data.setStatus(Fixed);
        mapper.updateBatch(data);
    }

    @Override
    public void reopen(String id) {
        mapper.reopenById(id, Reopened);
    }

    @Override
    public void close(String id) {
        mapper.closeById(id, Closed);
    }

    @Override
    public void remove(String id) {
        mapper.deleteById(id);
    }
}
