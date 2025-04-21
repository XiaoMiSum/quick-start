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
        var query = new LambdaQueryWrapperX<Bug>()
                .eq(Bug::getProjectId, req.getProjectId())
                .likeIfPresent(Bug::getTitle, req.getTitle())
                .eqIfPresent(Bug::getNodeId, req.getNodeId())
                .eqIfPresent(Bug::getFixer, req.getFixer())
                .eqIfPresent(Bug::getSupervisor, req.getSupervisor());
        switch (req.getTab()) {
            case ToMe -> query.eq(Bug::getHandler, req.getCurrentUser());
            case UnClosed -> query.ne(Bug::getStatus, Closed);
            case IsCreator -> query.eq(Bug::getCreatorId, req.getCurrentUser());
            case IsFixer -> query.eq(Bug::getFixer, req.getCurrentUser());
            case Fixed -> query.eq(Bug::getStatus, Fixed);
            case UnFixed -> query.notIn(Bug::getStatus, Fixed, Closed);
            case New -> query.eq(Bug::getStatus, New);
            case LongTime ->
                    query.lt(Bug::getAssignedTime, DateUtils.addTime(Duration.ofDays(-7))).ne(Bug::getStatus, Closed);
            case null, default -> {
                return selectPage(req, query.orderByDesc(Bug::getId));
            }
        }
        return selectPage(req, query.orderByDesc(Bug::getId));
    }

    default void reopenById(String id, BugStatus status, Long handler) {
        update(new LambdaUpdateWrapper<Bug>()
                .set(Bug::getStatus, status)
                .set(Bug::getHandler, handler)
                .set(Bug::getAssignedTime, LocalDateTime.now())
                .setSql(true, "reopened_times = reopened_times + 1")
                .eq(BaseDO::getId, id));

    }

    default void closeById(String id, BugStatus status, Long closer) {
        update(new LambdaUpdateWrapper<Bug>()
                .set(Bug::getStatus, status)
                .set(Bug::getHandler, null)
                .set(Bug::getClosedTime, LocalDateTime.now())
                .set(Bug::getCloser, closer)
                .eq(BaseDO::getId, id));
    }

    default Long selectCount(Long userId, BugStatus[] status) {
        return selectCount(new LambdaQueryWrapperX<Bug>()
                .eq(Bug::getHandler, userId)
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
