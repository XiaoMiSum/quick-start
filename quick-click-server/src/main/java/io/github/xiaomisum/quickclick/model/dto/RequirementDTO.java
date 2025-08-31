package io.github.xiaomisum.quickclick.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class RequirementDTO implements Serializable {

    private String id;

    @NotBlank(message = "标题不能为空")
    private String title;

    private String description;

    @NotNull(message = "所属项目不能为空")
    private String projectId;

    private String moduleId;

    private String prototypeUrl;

    private static final long serialVersionUID = 1L;
}