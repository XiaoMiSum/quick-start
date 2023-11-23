package org.example.controller.st.review.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReviewCasePageRespVO extends ReviewCaseBaseVO {

    private String path;

    private String chargeUser;

    private String reviewUser;

    private Long reviewer;

    private Date reviewTime;

    private String reviewResult;
}
