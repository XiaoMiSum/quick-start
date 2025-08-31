package io.github.xiaomisum.quickclick.controller.quality.testcase.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestcaseVersionCompareVO implements Serializable {

    private String field;

    private String oldValue;

    private String newValue;

    private static final long serialVersionUID = 1L;
}