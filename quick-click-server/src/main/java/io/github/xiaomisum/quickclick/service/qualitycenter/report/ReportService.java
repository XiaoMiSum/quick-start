package io.github.xiaomisum.quickclick.service.qualitycenter.report;

import io.github.xiaomisum.quickclick.model.dto.TestReportDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {

    /**
     * 获取测试报告列表
     *
     * @param projectId  项目ID
     * @param reportType 报告类型
     * @return 测试报告列表
     */
    List<TestReportDTO> getList(String projectId, String reportType);

    /**
     * 获取测试报告详情
     *
     * @param id 报告ID
     * @return 测试报告详情
     */
    TestReportDTO get(String id);

    /**
     * 生成测试报告
     *
     * @param projectId  项目ID
     * @param reportType 报告类型
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 报告ID
     */
    String generate(String projectId, String reportType, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 删除测试报告
     *
     * @param id 报告ID
     */
    void remove(String id);
}