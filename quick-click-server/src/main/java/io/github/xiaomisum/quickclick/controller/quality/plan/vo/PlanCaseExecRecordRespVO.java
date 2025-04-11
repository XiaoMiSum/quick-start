package io.github.xiaomisum.quickclick.controller.quality.plan.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.xiaomisum.quickclick.enums.TestStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static xyz.migoo.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlanCaseExecRecordRespVO extends PlanCaseExecRecordBaseVO {

    /**
     * 数据编号
     */
    private Long id;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime createTime;


    /**
     * 操作
     */
    private TestStatus operation;
}
