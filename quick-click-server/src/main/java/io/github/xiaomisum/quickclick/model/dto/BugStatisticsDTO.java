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

import java.util.List;
import java.util.Map;

@Data
public class BugStatisticsDTO {

    /**
     * 按状态统计
     */
    private Map<String, Integer> statusStatistics;

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
     * 按责任人统计
     */
    private Map<Long, Integer> supervisorStatistics;

    /**
     * 按处理人统计
     */
    private Map<Long, Integer> handlerStatistics;

    /**
     * 按创建人统计
     */
    private Map<Long, Integer> creatorStatistics;

    /**
     * 按日期统计（近30天）
     */
    private List<DailyBugStatistics> dailyStatistics;

    /**
     * 修复率统计
     */
    private Double fixRate;

    /**
     * 关闭率统计
     */
    private Double closeRate;

    /**
     * 平均修复时长（小时）
     */
    private Double avgFixDuration;

    /**
     * 激活次数统计
     */
    private Map<String, Integer> reopenStatistics;

    @Data
    public static class DailyBugStatistics {
        private String date;
        private Integer createdCount;
        private Integer fixedCount;
        private Integer closedCount;
    }
}