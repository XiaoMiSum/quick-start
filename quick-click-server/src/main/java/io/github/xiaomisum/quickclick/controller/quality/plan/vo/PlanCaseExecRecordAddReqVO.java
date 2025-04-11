package io.github.xiaomisum.quickclick.controller.quality.plan.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlanCaseExecRecordAddReqVO extends PlanCaseExecRecordBaseVO {

    /**
     * 项目编号
     */
    @NotBlank(message = "projectId 不能为空")
    private String projectId;

    /**
     * 计划编号
     */
    @NotBlank(message = "planId 不能为空")
    private String planId;

}
