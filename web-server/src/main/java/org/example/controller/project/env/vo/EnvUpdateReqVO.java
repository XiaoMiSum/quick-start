package org.example.controller.project.env.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EnvUpdateReqVO extends EnvBaseVO {

    @NotNull(message = "链接编号不能为空")
    private Long id;
}
