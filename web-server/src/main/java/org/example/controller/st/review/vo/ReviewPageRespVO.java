package org.example.controller.st.review.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import xyz.migoo.framework.jackson.databind.BigDecimalSerializer;

import java.math.BigDecimal;
import java.util.Date;

import static xyz.migoo.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReviewPageRespVO extends ReviewBaseVO {

    private Long id;

    private String speakUser;

    private String status;

    private int totalCase;

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal passedRate = BigDecimal.ZERO;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date actualStartTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date actualEndTime;
}
