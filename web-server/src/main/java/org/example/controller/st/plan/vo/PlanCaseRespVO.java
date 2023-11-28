package org.example.controller.st.plan.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.controller.st.testcase.vo.TestcaseStep;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlanCaseRespVO extends PlanCaseBaseVO {

    private Long id;

    private String path;

    private String chargeUser;

    private List<String> tags;

    private String precondition;

    private List<TestcaseStep> steps;

    private String executeResult;

}
