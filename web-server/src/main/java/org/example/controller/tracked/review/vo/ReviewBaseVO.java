package org.example.controller.tracked.review.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

import static xyz.migoo.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Data
public class ReviewBaseVO {

    private Long projectId;

    @NotBlank(message = "评审名称不能为空")
    private String name;

    @NotNull(message = "主讲人不能为空")
    private Long speaker;

    private List<String> reviewers;

    @NotNull(message = "预计开始时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date expectedStartTime;

    @NotNull(message = "预计结束时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date expectedEndTime;

}
