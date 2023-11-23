package org.example.controller.project.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectUpdateReqVO extends ProjectBaseVO {

    private Long id;
}
