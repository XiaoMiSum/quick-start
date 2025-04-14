package io.github.xiaomisum.quickclick.dal.mapper.qualitycenter;

import io.github.xiaomisum.quickclick.dal.dataobject.quality.Bug;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.BugExecRecord;
import io.github.xiaomisum.quickclick.enums.BugStatus;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;
import xyz.migoo.framework.mybatis.core.MPJLambdaWrapperX;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static io.github.xiaomisum.quickclick.enums.BugStatus.Closed;
import static io.github.xiaomisum.quickclick.enums.BugStatus.Reopened;

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

    default List<BugExecRecord> selectClosedBug(String projectId, Collection<Long> testers, LocalDateTime startTime, LocalDateTime endTime) {
        return selectList(new MPJLambdaWrapperX<BugExecRecord>()
                .leftJoin(Bug.class, on -> on.eq(BugExecRecord::getBugId, Bug::getId))
                .in(BugExecRecord::getUserId, testers)
                .ge(BugExecRecord::getCreateTime, startTime)
                .le(BugExecRecord::getCreateTime, endTime)
                .eq(BugExecRecord::getOperation, Closed)
                .eq(Bug::getStatus, Closed) // 有关闭记录的Bug可能被激活，这里需要过滤掉被激活的
                .eq(Bug::getProjectId, projectId));
    }

    default List<BugExecRecord> selectRecordsByOperation(String projectId, Collection<Long> closer, BugStatus operation,
                                                         LocalDateTime startTime, LocalDateTime endTime) {
        return selectList(new MPJLambdaWrapperX<BugExecRecord>()
                .leftJoin(Bug.class, on -> on.eq(BugExecRecord::getBugId, Bug::getId))
                .in(BugExecRecord::getUserId, closer)
                .ge(BugExecRecord::getCreateTime, startTime)
                .le(BugExecRecord::getCreateTime, endTime)
                .eq(BugExecRecord::getOperation, operation)
                .eq(Bug::getProjectId, projectId));
    }

}
