package io.github.xiaomisum.quickclick.controller.project.requirement.vo;

import io.github.xiaomisum.quickclick.controller.project.requirement.vo.RequirementBaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import xyz.migoo.framework.common.pojo.PageParam;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class RequirementQueryReqVO extends PageParam {

    private String title;

    private String projectId;

    private String moduleId;
}