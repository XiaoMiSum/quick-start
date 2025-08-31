package io.github.xiaomisum.quickclick.convert.qualitycenter.report;

import io.github.xiaomisum.quickclick.model.dto.TestReportDTO;
import io.github.xiaomisum.quickclick.dal.dataobject.report.ProjectWeekData;
import io.github.xiaomisum.quickclick.dal.dataobject.report.ProjectMonthData;
import io.github.xiaomisum.quickclick.model.dto.BugStatisticsDTO;
import io.github.xiaomisum.quickclick.model.dto.Statistics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TestReportConvert {

    TestReportConvert INSTANCE = Mappers.getMapper(TestReportConvert.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "projectId", target = "projectId")
    @Mapping(source = "startDate", target = "startTime")
    @Mapping(source = "endDate", target = "endTime")
    @Mapping(source = "createTime", target = "generateTime")
    TestReportDTO convert(ProjectWeekData weekData);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "projectId", target = "projectId")
    @Mapping(source = "startDate", target = "startTime")
    @Mapping(source = "endDate", target = "endTime")
    @Mapping(source = "createTime", target = "generateTime")
    TestReportDTO convert(ProjectMonthData monthData);

    // 转换缺陷统计数据
    TestReportDTO.BugStatistics convert(BugStatisticsDTO bugStatistics);

    // 转换执行统计数据
    Statistics convert(io.github.xiaomisum.quickclick.model.dto.Statistics statistics);
}