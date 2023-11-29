package org.example.controller.tracked.review.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.controller.tracked.testcase.vo.TestcaseStep;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReviewCaseRespVO extends ReviewCaseBaseVO {

    private Long id;

    private String path;

    private String chargeUser;

    private List<String> tags;

    private String precondition;

    private List<TestcaseStep> steps;

    private String reviewResult;

}
