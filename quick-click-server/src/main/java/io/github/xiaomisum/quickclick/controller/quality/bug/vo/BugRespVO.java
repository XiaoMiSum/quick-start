package io.github.xiaomisum.quickclick.controller.quality.bug.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static xyz.migoo.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugRespVO extends BugBaseVO {

    private String id;

    private String content;

    /**
     * 指派时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime assignedTime;

    /**
     * 确认时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime confirmedTime;

    /**
     * 修复人
     */
    private Long fixer;

    /**
     * 修复时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime fixedTime;

    /**
     * 关闭人
     */
    private Long closer;

    /**
     * 关闭时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
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
     * 解决方案详细描述
     */
    private String resolution;

    /**
     * 激活次数
     */
    private Integer reopenedTimes;

    /**
     * 来源
     */
    private String source;
}
