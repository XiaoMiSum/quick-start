package org.example.controller.project.module.vo;

import lombok.Data;

@Data
public class ModuleBaseVO {

    private Long projectId;

    private Long parentId;

    private String name;

    private Integer sort;
}
