package org.example.controller.st.plan.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static xyz.migoo.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Data
public class PlanBaseVO {

    private Long projectId;

    @NotBlank(message = "计划名称不能为空")
    private String name;

    @NotNull(message = "执行人不能为空")
    private Long executor;

    @NotNull(message = "预计开始时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date expectedStartTime;

    @NotNull(message = "预计结束时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date expectedEndTime;

}
