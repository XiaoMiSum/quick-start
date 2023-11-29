package org.example.controller.tracked.review.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.model.dto.Statistics;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReviewRespVO extends ReviewBaseVO {

    private Long id;

    private String memo;

    private Statistics statistics;

}
