package org.example.controller.st.testcase.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestcaseRespVO extends TestcaseBaseVO {

    private Long id;

    private List<TestcaseStep> steps;
}
