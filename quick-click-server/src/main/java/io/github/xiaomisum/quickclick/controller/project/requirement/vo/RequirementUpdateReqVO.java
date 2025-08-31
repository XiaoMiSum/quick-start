package io.github.xiaomisum.quickclick.controller.project.requirement.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class RequirementUpdateReqVO extends RequirementBaseVO {

    @NotNull(message = "需求ID不能为空")
    private String id;
}