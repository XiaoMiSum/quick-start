package org.example.controller.project.tag.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TagUpdateStatusReqVo {

    @NotNull(message = "标签编号不能为空")
    private Long id;

    @NotNull(message = "标签状态不能为空")
    private Integer status;

    private Long projectId;
}
