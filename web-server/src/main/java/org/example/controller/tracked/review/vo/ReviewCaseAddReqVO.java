package org.example.controller.tracked.review.vo;

import lombok.Data;

import java.util.List;

@Data
public class ReviewCaseAddReqVO {

    private Long reviewId;

    private List<Long> testcases;
}
