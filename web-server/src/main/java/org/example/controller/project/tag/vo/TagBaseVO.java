package org.example.controller.project.tag.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TagBaseVO {

    @NotNull(message = "项目编号不能为空")
    private Long projectId;

    @NotBlank(message = "标签名称不能为空")
    private String name;
}
