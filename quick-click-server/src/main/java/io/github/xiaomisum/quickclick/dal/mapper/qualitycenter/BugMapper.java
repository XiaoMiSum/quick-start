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
            case UnFixed -> query.ne(Bug::getStatus, Fixed);
            case New -> query.eq(Bug::getStatus, New);
            case LongTime ->
                    query.lt(Bug::getAssignedTime, DateUtils.addTime(Duration.ofDays(-7))).ne(Bug::getStatus, Closed);
            case null, default -> {
                return selectPage(req, query);
            }
        }
        return selectPage(req, query);
    }

    default void reopenById(String id, BugStatus status) {
        update(new LambdaUpdateWrapper<Bug>()
                .setSql(true, "status = ?, reopened_times = reopened_times + 1", status)
                .eq(BaseDO::getId, id));

    }

    default void closeById(String id, BugStatus status) {
        update(new LambdaUpdateWrapper<Bug>()
                .setSql(true, "status = ?, handler = null", status)
                .eq(BaseDO::getId, id));
    }
}
