package org.example.controller.tracked.review.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReviewUpdateReqVO extends ReviewBaseVO {

    private Long id;

    private String memo;
}
