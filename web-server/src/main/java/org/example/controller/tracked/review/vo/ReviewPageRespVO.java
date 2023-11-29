package org.example.controller.tracked.review.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.model.dto.Statistics;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static xyz.migoo.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReviewPageRespVO extends ReviewBaseVO {

    private Long id;

    private String speakUser;

    private Statistics statistics;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date actualStartTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date actualEndTime;
}
