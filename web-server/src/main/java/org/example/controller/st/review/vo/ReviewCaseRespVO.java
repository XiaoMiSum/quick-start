package org.example.controller.st.review.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.controller.st.testcase.vo.TestcaseStep;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReviewCaseRespVO extends ReviewCaseBaseVO {

    private String path;

    private List<String> tags;

    private String precondition;

    private List<TestcaseStep> steps;

}
