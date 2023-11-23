package org.example.controller.st.review.vo;

import lombok.Data;

import java.util.List;

@Data
public class ReviewCaseAddReqVO {

    private Long reviewId;

    private List<Long> testcases;
}
