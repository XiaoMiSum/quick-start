package io.github.xiaomisum.quickclick.dal.mapper.qualitycenter;

import io.github.xiaomisum.quickclick.dal.dataobject.quality.Bug;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.BugComment;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;
import xyz.migoo.framework.mybatis.core.MPJLambdaWrapperX;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static io.github.xiaomisum.quickclick.enums.BugStatus.Fixed;
import static io.github.xiaomisum.quickclick.enums.BugStatus.Reopened;

@Mapper
public interface BugCommentMapper extends BaseMapperX<BugComment> {

    default List<BugComment> selectList(String bugId) {
        return selectList(new LambdaQueryWrapperX<BugComment>()
                .eq(BugComment::getBugId, bugId)
                .orderByAsc(BugComment::getId));
    }

    default List<Bug> selectBugByReopen(String projectId, LocalDateTime startTime, LocalDateTime endTime) {
        return selectJoinList(Bug.class, new MPJLambdaWrapperX<BugComment>()
                .selectAll(Bug.class)
                .leftJoin(Bug.class, on -> on.eq(BugComment::getBugId, Bug::getId))
                .ge(BugComment::getCreateTime, startTime)
                .le(BugComment::getCreateTime, endTime)
                .eq(BugComment::getOperation, Reopened)
                .eq(Bug::getProjectId, projectId));
    }

    default List<Bug> selectBugByFixer(String projectId, Collection<Long> fixer, LocalDateTime startTime, LocalDateTime endTime) {
        return selectJoinList(Bug.class, new MPJLambdaWrapperX<BugComment>()
                .selectAs(BugComment::getContent, "fixDuration")
                .select(Bug::getId, Bug::getFixer, Bug::getSupervisor, Bug::getRejectedUser, Bug::getReopenedTimes)
                .leftJoin(Bug.class, on -> on.eq(BugComment::getBugId, Bug::getId))
                .in(BugComment::getUserId, fixer)
                .ge(BugComment::getCreateTime, startTime)
                .le(BugComment::getCreateTime, endTime)
                .eq(BugComment::getOperation, Fixed)
                .eq(Bug::getProjectId, projectId));
    }
}
