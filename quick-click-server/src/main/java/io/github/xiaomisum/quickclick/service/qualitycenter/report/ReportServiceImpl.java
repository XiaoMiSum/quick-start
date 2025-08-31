package io.github.xiaomisum.quickclick.service.qualitycenter.report;

import cn.hutool.core.util.IdUtil;
import io.github.xiaomisum.quickclick.model.dto.TestReportDTO;
import io.github.xiaomisum.quickclick.dal.dataobject.report.ProjectWeekData;
import io.github.xiaomisum.quickclick.dal.dataobject.report.ProjectMonthData;
import io.github.xiaomisum.quickclick.dal.dataobject.report.ProjectRangeDataBaseDO;
import io.github.xiaomisum.quickclick.dal.mapper.report.ProjectWeekDataMapper;
import io.github.xiaomisum.quickclick.dal.mapper.report.ProjectMonthDataMapper;
import io.github.xiaomisum.quickclick.service.project.ProjectService;
import io.github.xiaomisum.quickclick.service.qualitycenter.bug.BugService;
import io.github.xiaomisum.quickclick.service.qualitycenter.plan.PlanCaseService;
import io.github.xiaomisum.quickclick.service.qualitycenter.plan.PlanService;
import io.github.xiaomisum.quickclick.service.qualitycenter.review.ReviewCaseService;
import io.github.xiaomisum.quickclick.service.qualitycenter.review.ReviewService;
import io.github.xiaomisum.quickclick.service.qualitycenter.testcase.TestcaseService;
import io.github.xiaomisum.quickclick.model.dto.BugStatisticsDTO;
import io.github.xiaomisum.quickclick.model.dto.Statistics;
import io.github.xiaomisum.quickclick.convert.qualitycenter.report.TestReportConvert;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Resource
    private ProjectWeekDataMapper weekDataMapper;

    @Resource
    private ProjectMonthDataMapper monthDataMapper;

    @Resource
    private ProjectService projectService;

    @Resource
    private BugService bugService;

    @Resource
    private TestcaseService testcaseService;

    @Resource
    private PlanService planService;

    @Resource
    private PlanCaseService planCaseService;

    @Resource
    private ReviewService reviewService;

    @Resource
    private ReviewCaseService reviewCaseService;

    @Override
    public List<TestReportDTO> getList(String projectId, String reportType) {
        // 根据报告类型查询对应的报告数据
        if ("week".equals(reportType)) {
            List<ProjectWeekData> weekDataList = weekDataMapper.selectListByProjectId(projectId);
            // 转换为TestReportDTO
            return weekDataList.stream().map(this::convertToTestReportDTO).collect(Collectors.toList());
        } else if ("month".equals(reportType)) {
            List<ProjectMonthData> monthDataList = monthDataMapper.selectListByProjectId(projectId);
            // 转换为TestReportDTO
            return monthDataList.stream().map(this::convertToTestReportDTO).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public TestReportDTO get(String id) {
        // 根据ID查询报告详情
        try {
            Long reportId = Long.parseLong(id);
            // 先尝试查询周报数据
            ProjectWeekData weekData = weekDataMapper.selectById(reportId);
            if (weekData != null) {
                return convertToTestReportDTO(weekData);
            }
            // 再尝试查询月报数据
            ProjectMonthData monthData = monthDataMapper.selectById(reportId);
            if (monthData != null) {
                return convertToTestReportDTO(monthData);
            }
        } catch (NumberFormatException e) {
            // 如果ID不是数字格式，可以选择忽略或者记录日志
        }
        return null;
    }

    @Override
    public String generate(String projectId, String reportType, LocalDateTime startTime, LocalDateTime endTime) {
        // 生成测试报告
        Long reportId = IdUtil.getSnowflakeNextId();

        if ("week".equals(reportType)) {
            // 生成项目周报
            ProjectWeekData weekData = new ProjectWeekData();
            weekData.setId(reportId);
            weekData.setProjectId(projectId);
            weekData.setStartDate(startTime.toLocalDate());
            weekData.setEndDate(endTime.toLocalDate());

            // 填充统计数据
            populateReportData(weekData, projectId, startTime, endTime);

            weekDataMapper.insert(weekData);
        } else if ("month".equals(reportType)) {
            // 生成项目月报
            ProjectMonthData monthData = new ProjectMonthData();
            monthData.setId(reportId);
            monthData.setProjectId(projectId);
            monthData.setStartDate(startTime.toLocalDate());
            monthData.setEndDate(endTime.toLocalDate());

            // 填充统计数据
            populateReportData(monthData, projectId, startTime, endTime);

            monthDataMapper.insert(monthData);
        }

        return reportId.toString();
    }

    @Override
    public void remove(String id) {
        // 删除报告
        // 根据ID删除对应的报告数据
        try {
            Long reportId = Long.parseLong(id);
            // 尝试删除周报数据
            weekDataMapper.deleteById(reportId);
            // 尝试删除月报数据
            monthDataMapper.deleteById(reportId);
        } catch (NumberFormatException e) {
            // 如果ID不是数字格式，可以选择忽略或者记录日志
            // 这里简单忽略，因为在实际应用中可能需要更复杂的处理
        }
    }

    /**
     * 填充报告数据
     * 
     * @param reportData 报告数据对象
     * @param projectId  项目ID
     * @param startTime  开始时间
     * @param endTime    结束时间
     */
    private void populateReportData(ProjectRangeDataBaseDO reportData, String projectId, LocalDateTime startTime,
            LocalDateTime endTime) {
        // 获取缺陷统计数据
        BugStatisticsDTO bugStatistics = bugService.getStatistics(projectId, startTime, endTime);

        // 设置缺陷统计相关字段
        reportData.setThisRangeBugTotal(
                bugStatistics.getStatusStatistics().values().stream().mapToInt(Integer::intValue).sum());

        // 获取测试用例统计数据
        // 这里可以根据需要实现具体的统计逻辑

        // 设置其他字段...
        // 由于ProjectRangeDataBaseDO是基础类，具体的统计字段需要在子类中设置
    }

    /**
     * 将ProjectWeekData转换为TestReportDTO
     * 
     * @param weekData 周报数据
     * @return 测试报告DTO
     */
    private TestReportDTO convertToTestReportDTO(ProjectWeekData weekData) {
        TestReportDTO reportDTO = TestReportConvert.INSTANCE.convert(weekData);
        reportDTO.setProjectName(projectService.get(weekData.getProjectId()).getName());
        reportDTO.setReportType("week");
        reportDTO.setStartTime(weekData.getStartDate().atStartOfDay());
        reportDTO.setEndTime(weekData.getEndDate().atTime(23, 59, 59));

        // 设置测试用例统计数据和缺陷统计数据
        // 这里需要根据实际的业务逻辑来填充数据

        return reportDTO;
    }

    /**
     * 将ProjectMonthData转换为TestReportDTO
     * 
     * @param monthData 月报数据
     * @return 测试报告DTO
     */
    private TestReportDTO convertToTestReportDTO(ProjectMonthData monthData) {
        TestReportDTO reportDTO = TestReportConvert.INSTANCE.convert(monthData);
        reportDTO.setProjectName(projectService.get(monthData.getProjectId()).getName());
        reportDTO.setReportType("month");
        reportDTO.setStartTime(monthData.getStartDate().atStartOfDay());
        reportDTO.setEndTime(monthData.getEndDate().atTime(23, 59, 59));

        // 设置测试用例统计数据和缺陷统计数据
        // 这里需要根据实际的业务逻辑来填充数据

        return reportDTO;
    }
}