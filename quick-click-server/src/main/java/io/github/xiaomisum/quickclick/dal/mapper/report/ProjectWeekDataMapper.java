package io.github.xiaomisum.quickclick.dal.mapper.report;

import io.github.xiaomisum.quickclick.controller.report.vo.ProjectBugRespVO;
import io.github.xiaomisum.quickclick.controller.report.vo.ProjectTrendRespVO;
import io.github.xiaomisum.quickclick.controller.report.vo.QualificationRateRespVO;
import io.github.xiaomisum.quickclick.dal.dataobject.project.Project;
import io.github.xiaomisum.quickclick.dal.dataobject.report.DeveloperBasicData;
import io.github.xiaomisum.quickclick.dal.dataobject.report.ProjectMonthData;
import io.github.xiaomisum.quickclick.dal.dataobject.report.ProjectWeekData;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.infra.dal.dataobject.sys.User;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.MPJLambdaWrapperX;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ProjectWeekDataMapper extends BaseMapperX<ProjectWeekData> {

    default List<ProjectWeekData> selectList(LocalDate start, LocalDate end) {
        return selectList(new MPJLambdaWrapperX<ProjectWeekData>()
                .eq(DeveloperBasicData::getDate, start)
                .eq(DeveloperBasicData::getDate, end));
    }

    default List<QualificationRateRespVO.Item> selectQualificationRateList(LocalDate start, LocalDate end) {
        return selectJoinList(QualificationRateRespVO.Item.class, new MPJLambdaWrapperX<ProjectWeekData>()
                .select(ProjectWeekData::getProjectId, ProjectWeekData::getManagerId, ProjectWeekData::getQualificationRate)
                .selectAs(Project::getTitle, QualificationRateRespVO.Item::getProjectName)
                .selectAs(User::getName, QualificationRateRespVO.Item::getManagerName)
                .leftJoin(Project.class, on -> on.eq(ProjectMonthData::getProjectId, Project::getId))
                .leftJoin(User.class, on -> on.eq(ProjectMonthData::getManagerId, User::getId))
                .eq(ProjectWeekData::getStartDate, start)
                .eq(ProjectWeekData::getEndDate, end)
                .gt(ProjectWeekData::getThisRangeTestcaseTotal, 0));
    }

    default List<ProjectTrendRespVO.Item> selectQualificationRateTrendList() {
        return selectJoinList(ProjectTrendRespVO.Item.class, new MPJLambdaWrapperX<ProjectWeekData>()
                .select(ProjectWeekData::getStartDate, ProjectWeekData::getEndDate)
                .selectAvg(ProjectWeekData::getQualificationRate, ProjectWeekData::getQualificationRate)
                .gt(ProjectWeekData::getThisRangeTestcaseTotal, 0)
                .groupBy(ProjectWeekData::getStartDate)
                .groupBy(ProjectWeekData::getEndDate)
                .last("limit 5"));
    }

    default List<ProjectBugRespVO> selectBugList(LocalDate start, LocalDate end) {
        return selectJoinList(ProjectBugRespVO.class, new MPJLambdaWrapperX<ProjectWeekData>()
                .selectAll(ProjectWeekData.class)
                .selectAs(User::getName, ProjectBugRespVO::getManagerName)
                .selectAs(Project::getTitle, QualificationRateRespVO.Item::getProjectName)
                .leftJoin(User.class, on -> on.eq(ProjectWeekData::getManagerId, User::getId))
                .leftJoin(Project.class, on -> on.eq(ProjectWeekData::getProjectId, Project::getId))
                .eq(ProjectWeekData::getStartDate, start)
                .eq(ProjectWeekData::getEndDate, end));
    }

    default List<ProjectWeekData> selectBugList3(LocalDate start, LocalDate end) {
        return selectList(new MPJLambdaWrapperX<ProjectWeekData>()
                .select(ProjectWeekData::getStartDate, ProjectWeekData::getEndDate)
                .selectSum(ProjectWeekData::getThisRangeBugTotal)
                .eq(ProjectWeekData::getStartDate, start)
                .eq(ProjectWeekData::getEndDate, end)
                .groupBy(ProjectWeekData::getStartDate)
                .groupBy(ProjectWeekData::getEndDate)
                .last("limit 5"));
    }
}
