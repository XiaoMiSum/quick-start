package org.example.controller.tracked.plan.vo;

import lombok.Data;

import java.util.List;

@Data
public class PlanCaseAddReqVO {

    private Long planId;

    private List<Long> testcases;
}
