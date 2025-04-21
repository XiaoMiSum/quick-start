package io.github.xiaomisum.quickclick.dal.mapper.report;

import io.github.xiaomisum.quickclick.controller.project.days.vo.DaysQueryReqVO;
import io.github.xiaomisum.quickclick.controller.project.days.vo.DeveloperDaysPageRespVO;
import io.github.xiaomisum.quickclick.controller.report.vo.BugRateDetailRespVO;
import io.github.xiaomisum.quickclick.controller.report.vo.DeveloperBugReopenRespVO;
import io.github.xiaomisum.quickclick.controller.report.vo.DeveloperBugRespVO;
import io.github.xiaomisum.quickclick.dal.dataobject.project.Project;
import io.github.xiaomisum.quickclick.dal.dataobject.report.DeveloperBasicData;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.infra.dal.dataobject.sys.User;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.MPJLambdaWrapperX;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DeveloperBasicDataMapper extends BaseMapperX<DeveloperBasicData> {

    default PageResult<DeveloperDaysPageRespVO> selectPage(DaysQueryReqVO req) {
        return selectJoinPage(req, DeveloperDaysPageRespVO.class, new MPJLambdaWrapperX<DeveloperBasicData>()
                .selectX(User::getName)
                .selectAll(DeveloperBasicData.class)
                .leftJoin(User.class, on -> on.eq(DeveloperBasicData::getUserId, User::getId))
                .eq(DeveloperBasicData::getProjectId, req.getProjectId())
                .eqIfPresent(DeveloperBasicData::getDate, req.getDate())
                .eqIfPresent(DeveloperBasicData::getUserId, req.getUserId())
                .orderByDesc(DeveloperBasicData::getDate));
    }

    default List<DeveloperBasicData> selectSumList(LocalDate start, LocalDate end) {
        return selectList(new MPJLambdaWrapperX<DeveloperBasicData>()
                .select(DeveloperBasicData::getProjectId)
                .selectSum(DeveloperBasicData::getTestcaseTotal)
                .selectSum(DeveloperBasicData::getNewBugTotal)
                .selectSum(DeveloperBasicData::getClosedBugTotal)
                .ge(DeveloperBasicData::getDate, start)
                .le(DeveloperBasicData::getDate, end)
                .groupBy(DeveloperBasicData::getProjectId));
    }

    default List<DeveloperBugRespVO.Item> selectSumList2(LocalDate start, LocalDate end) {
        return selectJoinList(DeveloperBugRespVO.Item.class, new MPJLambdaWrapperX<DeveloperBasicData>()
                .selectX(DeveloperBasicData::getUserId)
                .selectX(User::getName)
                .selectSum(DeveloperBasicData::getNewBugTotal)
                .leftJoin(User.class, on -> on.eq(DeveloperBasicData::getUserId, User::getId))
                .ge(DeveloperBasicData::getDate, start)
                .le(DeveloperBasicData::getDate, end)
                .groupBy(DeveloperBasicData::getUserId)
                .groupBy(User::getName));
    }

    default List<DeveloperBugReopenRespVO> selectSumList3(LocalDate start, LocalDate end) {
        return selectJoinList(DeveloperBugReopenRespVO.class, new MPJLambdaWrapperX<DeveloperBasicData>()
                .selectX(DeveloperBasicData::getUserId)
                .selectSum(DeveloperBasicData::getReopenedBugTotal)
                .ge(DeveloperBasicData::getDate, start)
                .le(DeveloperBasicData::getDate, end)
                .gt(DeveloperBasicData::getReopenedBugTotal, 0)
                .groupBy(DeveloperBasicData::getUserId));
    }

    default List<DeveloperBasicData> selectSumList4(LocalDate start, LocalDate end) {
        return selectList(new MPJLambdaWrapperX<DeveloperBasicData>()
                .selectX(DeveloperBasicData::getUserId)
                .selectSum(DeveloperBasicData::getFixedBugDuration)
                .selectSum(DeveloperBasicData::getFixedBugTotal)
                .ge(DeveloperBasicData::getDate, start)
                .le(DeveloperBasicData::getDate, end)
                .gt(DeveloperBasicData::getFixedBugDuration, 0)
                .groupBy(DeveloperBasicData::getUserId));
    }

    default List<DeveloperBasicData> selectSumList5(LocalDate start, LocalDate end) {
        return selectList(new MPJLambdaWrapperX<DeveloperBasicData>()
                .selectX(DeveloperBasicData::getUserId)
                .selectSum(DeveloperBasicData::getTestcaseTotal)
                .selectSum(DeveloperBasicData::getNewBugTotal)
                .ge(DeveloperBasicData::getDate, start)
                .le(DeveloperBasicData::getDate, end)
                .gt(DeveloperBasicData::getFixedBugDuration, 0)
                .groupBy(DeveloperBasicData::getUserId));
    }

    default List<BugRateDetailRespVO> selectList(LocalDate start, LocalDate end, Long userId) {
        return selectJoinList(BugRateDetailRespVO.class, new MPJLambdaWrapperX<DeveloperBasicData>()
                .selectX(DeveloperBasicData::getUserId, DeveloperBasicData::getProjectId)
                .selectSum(DeveloperBasicData::getTestcaseTotal, BugRateDetailRespVO::getTestcaseTotal)
                .selectSum(DeveloperBasicData::getNewBugTotal, BugRateDetailRespVO::getBugTotal)
                .selectAs(Project::getTitle, BugRateDetailRespVO::getProjectName)
                .leftJoin(Project.class, on -> on.eq(DeveloperBasicData::getProjectId, Project::getId))
                .ge(DeveloperBasicData::getDate, start)
                .le(DeveloperBasicData::getDate, end)
                .eq(DeveloperBasicData::getUserId, userId)
                .groupBy(DeveloperBasicData::getUserId)
                .groupBy(DeveloperBasicData::getProjectId)
                .groupBy(Project::getTitle)
        );
    }
}
