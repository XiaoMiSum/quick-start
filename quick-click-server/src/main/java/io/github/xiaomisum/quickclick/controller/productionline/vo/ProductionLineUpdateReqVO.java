package io.github.xiaomisum.quickclick.controller.productionline.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductionLineUpdateReqVO extends ProductionLineBaseVO {

    @NotBlank(message = "id 不能为空")
    private String id;
}
