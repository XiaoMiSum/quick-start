package io.github.xiaomisum.quickclick.dal.mapper.qualitycenter;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import io.github.xiaomisum.quickclick.controller.quality.bug.vo.BugQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Bug;
import io.github.xiaomisum.quickclick.enums.BugStatus;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.util.date.DateUtils;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static io.github.xiaomisum.quickclick.enums.BugStatus.*;

@Mapper
public interface BugMapper extends BaseMapperX<Bug> {

    default PageResult<Bug> selectPage(BugQueryReqVO req) {
        return selectPage(req, new LambdaQueryWrapperX<Bug>()
                .eq(Bug::getProjectId, req.getProjectId())
                .eqIfPresent(Bug::getNodeId, req.getNodeId())
                .likeIfPresent(Bug::getTitle, req.getTitle())
                .eqIfPresent(Bug::getSupervisor, req.getSupervisor())
                .eqIfPresent(Bug::getFixer, req.getFixer())
                .eqIfPresent(Bug::getTestcaseId, req.getTestcaseId()) // 添加测试用例ID查询条件
                .apply(req.getCurrentUser() != null, "supervisor = {0} or handler = {0} or creator_id = {0}",
                        req.getCurrentUser())
                .apply("tab = 'All' or tab = {0}", req.getTab()));
    }

    default List<Bug> selectList(BugQueryReqVO req) {
        return selectList(new LambdaQueryWrapperX<Bug>()
                .eq(Bug::getProjectId, req.getProjectId())
                .eqIfPresent(Bug::getNodeId, req.getNodeId())
                .likeIfPresent(Bug::getTitle, req.getTitle())
                .eqIfPresent(Bug::getSupervisor, req.getSupervisor())
                .eqIfPresent(Bug::getFixer, req.getFixer())
                .eqIfPresent(Bug::getTestcaseId, req.getTestcaseId())); // 添加测试用例ID查询条件
    }

    default void updateBatch(List<Bug> bugs) {
        bugs.forEach(this::updateById);
    }

    default void reopenById(String id, BugStatus status, Long handler) {
        update(new LambdaUpdateWrapper<Bug>()
                .set(Bug::getStatus, status)
                .set(Bug::getReopenedTimes, 1)
                .set(Bug::getHandler, handler)
                .eq(BaseDO::getId, id));
    }

    default void closeById(String id, BugStatus closed, Long loginUserId) {
        update(new LambdaUpdateWrapper<Bug>()
                .set(Bug::getStatus, closed)
                .set(Bug::getCloser, loginUserId)
                .set(Bug::getClosedTime, LocalDateTime.now())
                .eq(BaseDO::getId, id));
    }

    default Long selectCount(Long userId, BugStatus... status) {
        return selectCount(new LambdaQueryWrapperX<Bug>()
                .eq(Bug::getSupervisor, userId)
                .in(Bug::getStatus, Arrays.stream(status).toList()));
    }

    default List<Bug> selectListBySupervisor(String projectId, Collection<Long> supervisor,
            LocalDateTime startTime, LocalDateTime endTime) {
        return selectList(new LambdaQueryWrapperX<Bug>()
                .eq(Bug::getProjectId, projectId)
                .in(Bug::getSupervisor, supervisor)
                .ge(Bug::getCreateTime, startTime)
                .le(Bug::getCreateTime, endTime));
    }

    default List<Bug> selectListByClose(String projectId, LocalDateTime startTime, LocalDateTime endTime) {
        return selectList(new LambdaQueryWrapperX<Bug>()
                .eq(Bug::getStatus, Closed)
                .eq(Bug::getProjectId, projectId)
                .ge(Bug::getClosedTime, startTime)
                .le(Bug::getClosedTime, endTime));
    }

    default List<Bug> selectListByCreator(String projectId, Collection<Long> creator,
            LocalDateTime startTime, LocalDateTime endTime) {
        return selectList(new LambdaQueryWrapperX<Bug>()
                .eq(Bug::getProjectId, projectId)
                .in(Bug::getCreatorId, creator)
                .ge(Bug::getCreateTime, startTime)
                .le(Bug::getCreateTime, endTime));
    }

    default List<Bug> selectUncloseList(LocalDateTime startTime, LocalDateTime endTime) {
        return selectList(new LambdaQueryWrapperX<Bug>()
                .ne(Bug::getStatus, Closed)
                .ge(Bug::getCreateTime, startTime)
                .le(Bug::getCreateTime, endTime));
    }

    default List<Bug> selectListByIds(List<String> ids) {
        return selectList(new LambdaQueryWrapperX<Bug>()
                .in(Bug::getId, ids));
    }

}
