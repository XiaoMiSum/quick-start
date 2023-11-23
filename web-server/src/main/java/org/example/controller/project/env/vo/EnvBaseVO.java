package org.example.controller.project.env.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EnvBaseVO {

    private Long projectId;

    @NotBlank(message = "环境类型不能为空")
    private String type;

    @NotBlank(message = "协议不能为空")
    private String protocol;

    @NotBlank(message = "主机地址不能为空")
    private String host;

    private String port;

    private String memo;
}
