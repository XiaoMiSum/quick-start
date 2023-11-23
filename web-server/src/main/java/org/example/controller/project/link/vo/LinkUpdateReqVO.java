package org.example.controller.project.link.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LinkUpdateReqVO extends LinkBaseVO {

    @NotNull(message = "链接编号不能为空")
    private Long id;
}
