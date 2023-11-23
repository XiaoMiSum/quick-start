package org.example.controller.project.link.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LinkBaseVO {

    private Long projectId;

    @NotBlank(message = "链接类型不能为空")
    private String type;

    @NotBlank(message = "链接地址不能为空")
    private String link;

    private String memo;
}
