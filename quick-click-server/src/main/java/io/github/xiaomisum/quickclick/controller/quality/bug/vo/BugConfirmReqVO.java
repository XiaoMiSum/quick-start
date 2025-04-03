package io.github.xiaomisum.quickclick.controller.quality.bug.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static xyz.migoo.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BugConfirmReqVO {

    /**
     * 缺陷编号
     */
    @NotBlank(message = "id 不能为空")
    private String id;

    /**
     * 优先级
     */
    @NotBlank(message = "priority 不能为空")
    private String priority;

    /**
     * 责任人
     */
    @NotNull(message = "supervisor 不能为空")
    private Long supervisor;

    /**
     * 指派处理人
     */
    @NotNull(message = "handler 不能为空")
    private Long handler;

    /**
     * 指派时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime assignedTime;

    /**
     * 评论信息
     */
    private String comment;
}
