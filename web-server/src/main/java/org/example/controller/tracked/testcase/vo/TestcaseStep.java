package org.example.controller.tracked.testcase.vo;

import lombok.Data;

@Data
public class TestcaseStep {

    private String exec;

    private String expected;

    private String actual;
}
