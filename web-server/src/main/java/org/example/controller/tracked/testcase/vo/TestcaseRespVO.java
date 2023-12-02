package org.example.controller.tracked.testcase.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestcaseRespVO extends TestcaseBaseVO {

    private Long id;

    private String reviewed;

    private List<TestcaseStep> steps;
}
