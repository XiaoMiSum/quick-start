package io.github.xiaomisum.quickclick.controller.quality.bug.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.xiaomisum.quickclick.enums.BugStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static xyz.migoo.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@EqualsAndHashCode(callSuper = true)
@Data
public class BugCommentRespVO extends BugCommentBaseVO {

    private Long id;

    /**
     * 用户编号
     */
    private Long userId;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime createTime;

    /**
     * 操作
     */
    private BugStatus operation;

}
