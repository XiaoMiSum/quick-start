package io.github.xiaomisum.quickclick.controller.project.requirement.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class RequirementRespVO extends RequirementBaseVO {

    private String id;

    // 添加项目名称和模块名称字段
    private String projectName;

    private String moduleName;
}