package org.example.controller.project.tag.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TagRespVO extends TagBaseVO {

    private Long id;

    private Integer status;
}
