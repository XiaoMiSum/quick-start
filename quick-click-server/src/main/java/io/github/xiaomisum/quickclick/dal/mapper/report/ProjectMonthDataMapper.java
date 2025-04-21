package io.github.xiaomisum.quickclick.dal.mapper.report;

import io.github.xiaomisum.quickclick.controller.report.vo.ProjectBugRespVO;
import io.github.xiaomisum.quickclick.controller.report.vo.ProjectTrendRespVO;
import io.github.xiaomisum.quickclick.controller.report.vo.QualificationRateRespVO;
import io.github.xiaomisum.quickclick.dal.dataobject.project.Project;
import io.github.xiaomisum.quickclick.dal.dataobject.report.DeveloperBasicData;
import io.github.xiaomisum.quickclick.dal.dataobject.report.ProjectMonthData;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.infra.dal.dataobject.sys.User;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.MPJLambdaWrapperX;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ProjectMonthDataMapper extends BaseMapperX<ProjectMonthData> {

    default List<ProjectMonthData> selectList(LocalDate start, LocalDate end) {
        return selectList(new MPJLambdaWrapperX<ProjectMonthData>()
                .eq(DeveloperBasicData::getDate, start)
                .eq(DeveloperBasicData::getDate, end));
    }

    default List<QualificationRateRespVO.Item> selectQualificationRateList(LocalDate start, LocalDate end) {
        return selectJoinList(QualificationRateRespVO.Item.class, new MPJLambdaWrapperX<ProjectMonthData>()
                .select(ProjectMonthData::getProjectId, ProjectMonthData::getManagerId, ProjectMonthData::getQualificationRate)
                .selectAs(Project::getTitle, QualificationRateRespVO.Item::getProjectName)
                .selectAs(User::getName, QualificationRateRespVO.Item::getManagerName)
                .leftJoin(Project.class, on -> on.eq(ProjectMonthData::getProjectId, Project::getId))
                .leftJoin(User.class, on -> on.eq(ProjectMonthData::getManagerId, User::getId))
                .eq(ProjectMonthData::getStartDate, start)
                .eq(ProjectMonthData::getEndDate, end)
                .gt(ProjectMonthData::getThisRangeTestcaseTotal, 0));
    }

    default List<ProjectTrendRespVO.Item> selectQualificationRateTrendList() {
        return selectJoinList(ProjectTrendRespVO.Item.class, new MPJLambdaWrapperX<ProjectMonthData>()
                .select(ProjectMonthData::getStartDate, ProjectMonthData::getEndDate)
                .selectAvg(ProjectMonthData::getQualificationRate, ProjectMonthData::getQualificationRate)
                .gt(ProjectMonthData::getThisRangeTestcaseTotal, 0)
                .groupBy(ProjectMonthData::getStartDate)
                .groupBy(ProjectMonthData::getEndDate)
                .last("limit 5"));
    }

    default List<ProjectBugRespVO> selectBugList(LocalDate start, LocalDate end) {
        return selectJoinList(ProjectBugRespVO.class, new MPJLambdaWrapperX<ProjectMonthData>()
                .selectAll(ProjectMonthData.class)
                .selectAs(User::getName, ProjectBugRespVO::getManagerName)
                .selectAs(Project::getTitle, QualificationRateRespVO.Item::getProjectName)
                .leftJoin(User.class, on -> on.eq(ProjectMonthData::getManagerId, User::getId))
                .leftJoin(Project.class, on -> on.eq(ProjectMonthData::getProjectId, Project::getId))
                .eq(ProjectMonthData::getStartDate, start)
                .eq(ProjectMonthData::getEndDate, end));
    }

    default List<ProjectMonthData> selectBugList3(LocalDate start, LocalDate end) {
        return selectList(new MPJLambdaWrapperX<ProjectMonthData>()
                .select(ProjectMonthData::getStartDate, ProjectMonthData::getEndDate)
                .selectSum(ProjectMonthData::getThisRangeBugTotal)
                .eq(ProjectMonthData::getStartDate, start)
                .eq(ProjectMonthData::getEndDate, end)
                .groupBy(ProjectMonthData::getStartDate)
                .groupBy(ProjectMonthData::getEndDate)
                .last("limit 5"));
    }
}
