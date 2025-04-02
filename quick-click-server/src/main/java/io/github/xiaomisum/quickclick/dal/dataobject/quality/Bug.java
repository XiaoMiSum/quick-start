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

package io.github.xiaomisum.quickclick.dal.dataobject.quality;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.xiaomisum.quickclick.enums.BugStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "qc_quality_bug", autoResultMap = true)
@Data
public class Bug extends BaseDO<String> {

    /**
     * 标题
     */
    private String title;

    /**
     * 项目编号
     */
    private String projectId;

    /**
     * 测试计划编号
     */
    private String planId;

    /**
     * 测试用例编号
     */
    private String testcaseId;

    /**
     * 模块编号
     */
    private String nodeId;

    /**
     * 严重等级
     */
    private String severity;

    /**
     * 优先级
     */
    private String priority;

    /**
     * 状态
     */
    private BugStatus status;

    /**
     * 详细描述
     */
    private String content;

    /**
     * 责任人
     */
    private Long supervisor;

    /**
     * 处理人
     */
    private Long handler;

    /**
     * 指派时间
     */
    private LocalDateTime assignedTime;

    /**
     * 确认时间
     */
    private LocalDateTime confirmedTime;

    /**
     * 修复人
     */
    private Long fixer;

    /**
     * 修复时间
     */
    private LocalDateTime fixedTime;

    /**
     * 修复时长
     */
    private BigDecimal fixDuration;

    /**
     * 关闭人
     */
    private Long closer;

    /**
     * 关闭时间
     */
    private LocalDateTime closedTime;

    /**
     * 产生原因
     */
    private String cause;

    /**
     * 产生原因详细描述
     */
    private String rootCause;

    /**
     * 解决方案
     */
    private String solution;

    /**
     * 激活次数
     */
    private Integer reopenedTimes;

    /**
     * 来源
     */
    private String source;
    /**
     * 创建人
     */
    private Long creatorId;
}
