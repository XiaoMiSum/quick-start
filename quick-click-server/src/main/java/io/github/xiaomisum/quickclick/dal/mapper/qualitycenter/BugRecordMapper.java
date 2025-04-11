package io.github.xiaomisum.quickclick.dal.mapper.qualitycenter;

import io.github.xiaomisum.quickclick.dal.dataobject.quality.Bug;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.BugExecRecord;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;
import xyz.migoo.framework.mybatis.core.MPJLambdaWrapperX;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static io.github.xiaomisum.quickclick.enums.BugStatus.*;

@Mapper
public interface BugRecordMapper extends BaseMapperX<BugExecRecord> {

    default List<BugExecRecord> selectList(String bugId) {
        return selectList(new LambdaQueryWrapperX<BugExecRecord>()
                .eq(BugExecRecord::getBugId, bugId)
                .orderByAsc(BugExecRecord::getId));
    }

    default List<Bug> selectBugByReopen(String projectId, LocalDateTime startTime, LocalDateTime endTime) {
        return selectJoinList(Bug.class, new MPJLambdaWrapperX<BugExecRecord>()
                .selectAll(Bug.class)
                .leftJoin(Bug.class, on -> on.eq(BugExecRecord::getBugId, Bug::getId))
                .ge(BugExecRecord::getCreateTime, startTime)
                .le(BugExecRecord::getCreateTime, endTime)
                .eq(BugExecRecord::getOperation, Reopened)
                .eq(Bug::getProjectId, projectId));
    }

    default List<Bug> selectBugByFixer(String projectId, Collection<Long> fixer, LocalDateTime startTime, LocalDateTime endTime) {
        return selectJoinList(Bug.class, new MPJLambdaWrapperX<BugExecRecord>()
                .selectAs(BugExecRecord::getContent, "fixDuration")
                .select(Bug::getId, Bug::getFixer, Bug::getSupervisor, Bug::getRejectedUser, Bug::getReopenedTimes)
                .leftJoin(Bug.class, on -> on.eq(BugExecRecord::getBugId, Bug::getId))
                .in(BugExecRecord::getUserId, fixer)
                .ge(BugExecRecord::getCreateTime, startTime)
                .le(BugExecRecord::getCreateTime, endTime)
                .eq(BugExecRecord::getOperation, Fixed)
                .eq(Bug::getProjectId, projectId));
    }

    default List<BugExecRecord> selectCloseRecords(String projectId, Collection<Long> closer, LocalDateTime startTime, LocalDateTime endTime) {
        return selectList(new MPJLambdaWrapperX<BugExecRecord>()
                .in(BugExecRecord::getUserId, closer)
                .ge(BugExecRecord::getCreateTime, startTime)
                .le(BugExecRecord::getCreateTime, endTime)
                .eq(BugExecRecord::getOperation, Closed)
                .eq(Bug::getProjectId, projectId));
    }

    default List<BugExecRecord> selectReopenRecords(String projectId, Collection<Long> users, LocalDateTime startTime, LocalDateTime endTime) {
        return selectList(new MPJLambdaWrapperX<BugExecRecord>()
                .in(BugExecRecord::getUserId, users)
                .ge(BugExecRecord::getCreateTime, startTime)
                .le(BugExecRecord::getCreateTime, endTime)
                .eq(BugExecRecord::getOperation, Reopened)
                .eq(Bug::getProjectId, projectId));
    }
}
