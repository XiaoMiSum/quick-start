/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2025.  Lorem XiaoMiSum (mi_xiao@qq.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * 'Software'), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.github.xiaomisum.quickclick.controller.quality.report;

import io.github.xiaomisum.quickclick.model.dto.TestReportDTO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.Result;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 测试报告
 */
@RestController
@RequestMapping("/quality-center/report")
public class ReportController {

    @Resource
    private io.github.xiaomisum.quickclick.service.qualitycenter.report.ReportService service;

    /**
     * 获取测试报告列表
     *
     * @param projectId  项目ID
     * @param reportType 报告类型
     * @return 测试报告列表
     */
    @GetMapping
    public Result<List<TestReportDTO>> getList(@RequestParam("projectId") String projectId,
            @RequestParam(value = "reportType", required = false) String reportType) {
        List<TestReportDTO> reports = service.getList(projectId, reportType);
        return Result.getSuccessful(reports);
    }

    /**
     * 获取测试报告详情
     *
     * @param id 报告ID
     * @return 测试报告详情
     */
    @GetMapping("/{id}")
    public Result<TestReportDTO> get(@PathVariable("id") String id) {
        TestReportDTO report = service.get(id);
        return Result.getSuccessful(report);
    }

    /**
     * 生成测试报告
     *
     * @param projectId  项目ID
     * @param reportType 报告类型
     * @param startDate  开始日期
     * @param endDate    结束日期
     * @return 报告ID
     */
    @PostMapping("/generate")
    public Result<String> generate(@RequestParam("projectId") String projectId,
            @RequestParam("reportType") String reportType,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        // 如果没有指定日期范围，根据报告类型设置默认范围
        LocalDateTime start = LocalDateTime.now().minusDays(7); // 默认一周前
        LocalDateTime end = LocalDateTime.now();

        if (startDate != null && !startDate.isEmpty()) {
            start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
        }

        if (endDate != null && !endDate.isEmpty()) {
            end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atTime(23, 59, 59);
        }

        String reportId = service.generate(projectId, reportType, start, end);
        return Result.getSuccessful(reportId);
    }

    /**
     * 删除测试报告
     *
     * @param id 报告ID
     * @return 处理结果
     */
    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable("id") String id) {
        service.remove(id);
        return Result.getSuccessful();
    }
}