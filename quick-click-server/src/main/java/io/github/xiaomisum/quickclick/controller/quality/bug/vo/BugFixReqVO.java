package io.github.xiaomisum.quickclick.controller.quality.bug.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import xyz.migoo.framework.jackson.databind.BigDecimalSerializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static xyz.migoo.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BugFixReqVO {

    /**
     * 缺陷编号
     */
    @NotBlank(message = "id 不能为空")
    private String id;

    /**
     * 修复时长
     */
    @NotNull(message = "fixDuration 不能为空")
    @DecimalMin(value = "0.01", message = "fixDuration 最小为0.01")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal fixDuration;

    /**
     * 产生原因
     */
    @NotBlank(message = "cause 不能为空")
    private String cause;

    /**
     * 产生原因详细描述
     */
    @NotBlank(message = "causeDetailed 不能为空")
    private String causeDetailed;

    /**
     * 解决方案
     */
    @NotBlank(message = "solution 不能为空")
    private String solution;

    /**
     * 指派处理人
     */
    @NotNull(message = "handler 不能为空")
    private Long handler;

    /**
     * 解决时间
     */
    @NotNull(message = "fixedTime 不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime fixedTime;
}
