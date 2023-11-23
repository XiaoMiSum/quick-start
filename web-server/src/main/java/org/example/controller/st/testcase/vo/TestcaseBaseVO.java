package org.example.controller.st.testcase.vo;

import lombok.Data;

import java.util.List;

@Data
public class TestcaseBaseVO {

    private Long projectId;

    private Long moduleId;

    private String name;

    private String level;

    private String precondition;


    private List<String> tags;

    private Long chargeUserId;
}
