package io.github.xiaomisum.quickclick.controller.productionline.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductionLineBaseVO {

    @NotBlank(message = "title 不能为空")
    private String title;

    @NotNull(message = "manager 不能为空")
    private Long manager;

    private String memo;
}
