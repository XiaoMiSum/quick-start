package io.github.xiaomisum.quickclick.controller.project.requirement.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class RequirementBaseVO implements Serializable {

    @NotBlank(message = "标题不能为空")
    private String title;

    private String description;

    @NotNull(message = "所属项目不能为空")
    private String projectId;

    private String moduleId;

    private String prototypeUrl;

    // 添加创建时间和更新时间字段
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}