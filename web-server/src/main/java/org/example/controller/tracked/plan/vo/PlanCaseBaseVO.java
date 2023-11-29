package org.example.controller.tracked.plan.vo;

import lombok.Data;

import java.util.List;

@Data
public class PlanCaseBaseVO {

    private Long projectId;

    private Long planId;

    private Long moduleId;

    private Long caseId;

    private String name;

    private String level;

    private List<String> tags;

    private Long chargeUserId;
}
