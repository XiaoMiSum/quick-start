package org.example.controller.st.plan.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.controller.st.testcase.vo.TestcaseStep;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanCaseExecuteVO {

    private Long id;

    private Long caseId;

    private Long planId;

    private String result;

    private Long executor;
    
    private List<TestcaseStep> steps;
}
