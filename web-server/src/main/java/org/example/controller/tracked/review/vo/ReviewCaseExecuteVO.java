package org.example.controller.tracked.review.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCaseExecuteVO {

    private Long id;

    private Long caseId;

    private Long reviewId;

    private String result;

    private Long reviewer;
}
