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

package io.github.xiaomisum.quickclick.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class TestReportDTO {

    /**
     * 报告ID
     */
    private String id;

    /**
     * 报告名称
     */
    private String title;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 报告类型 (项目周报、项目月报、测试计划报告、测试评审报告)
     */
    private String reportType;

    /**
     * 报告周期开始时间
     */
    private LocalDateTime startTime;

    /**
     * 报告周期结束时间
     */
    private LocalDateTime endTime;

    /**
     * 测试用例统计数据
     */
    private TestCaseStatistics testCaseStatistics;

    /**
     * 缺陷统计数据
     */
    private BugStatistics bugStatistics;

    /**
     * 测试计划执行情况
     */
    private List<PlanExecution> planExecutions;

    /**
     * 测试评审情况
     */
    private List<ReviewExecution> reviewExecutions;

    /**
     * 报告生成时间
     */
    private LocalDateTime generateTime;

    @Data
    public static class TestCaseStatistics {
        /**
         * 总用例数
         */
        private Integer totalCases;

        /**
         * 新增用例数
         */
        private Integer newCases;

        /**
         * 执行用例数
         */
        private Integer executedCases;

        /**
         * 通过用例数
         */
        private Integer passedCases;

        /**
         * 失败用例数
         */
        private Integer failedCases;

        /**
         * 阻塞用例数
         */
        private Integer blockedCases;
    }

    @Data
    public static class BugStatistics {
        /**
         * 总缺陷数
         */
        private Integer totalBugs;

        /**
         * 新增缺陷数
         */
        private Integer newBugs;

        /**
         * 已修复缺陷数
         */
        private Integer fixedBugs;

        /**
         * 已关闭缺陷数
         */
        private Integer closedBugs;

        /**
         * 按严重等级统计
         */
        private Map<String, Integer> severityStatistics;

        /**
         * 按优先级统计
         */
        private Map<String, Integer> priorityStatistics;

        /**
         * 按模块统计
         */
        private Map<String, Integer> moduleStatistics;

        /**
         * 缺陷修复率
         */
        private Double fixRate;

        /**
         * 缺陷关闭率
         */
        private Double closeRate;
    }

    @Data
    public static class PlanExecution {
        /**
         * 计划ID
         */
        private String planId;

        /**
         * 计划名称
         */
        private String planName;

        /**
         * 计划开始时间
         */
        private LocalDateTime planStartTime;

        /**
         * 计划结束时间
         */
        private LocalDateTime planEndTime;

        /**
         * 实际开始时间
         */
        private LocalDateTime actualStartTime;

        /**
         * 实际结束时间
         */
        private LocalDateTime actualEndTime;

        /**
         * 计划状态
         */
        private String status;

        /**
         * 执行统计
         */
        private Statistics executionStatistics;
    }

    @Data
    public static class ReviewExecution {
        /**
         * 评审ID
         */
        private String reviewId;

        /**
         * 评审名称
         */
        private String reviewName;

        /**
         * 评审开始时间
         */
        private LocalDateTime reviewStartTime;

        /**
         * 评审结束时间
         */
        private LocalDateTime reviewEndTime;

        /**
         * 实际开始时间
         */
        private LocalDateTime actualStartTime;

        /**
         * 实际结束时间
         */
        private LocalDateTime actualEndTime;

        /**
         * 评审状态
         */
        private String status;

        /**
         * 评审统计
         */
        private Statistics reviewStatistics;
    }
}