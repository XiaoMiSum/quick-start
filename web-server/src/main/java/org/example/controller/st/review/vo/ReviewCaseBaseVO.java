package org.example.controller.st.review.vo;

import lombok.Data;

import java.util.List;

@Data
public class ReviewCaseBaseVO {

    private Long projectId;

    private Long reviewId;

    private Long moduleId;

    private Long caseId;

    private String name;

    private String level;

    private List<String> tags;

    private Long chargeUserId;
}
