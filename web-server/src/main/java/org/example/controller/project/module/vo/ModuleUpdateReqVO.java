package org.example.controller.project.module.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ModuleUpdateReqVO extends ModuleBaseVO {
    private Long id;
}
