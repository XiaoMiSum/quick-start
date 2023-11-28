package org.example.controller.st.plan.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlanCasePageRespVO extends PlanCaseBaseVO {

    private Long id;

    private String path;

    private String chargeUser;

    private String executorUser;

    private Long executor;

    private Date executeTime;

    private String executeResult;

    private String reviewed;
}
