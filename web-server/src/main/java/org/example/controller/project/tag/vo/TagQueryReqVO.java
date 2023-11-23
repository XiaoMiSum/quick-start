package org.example.controller.project.tag.vo;

import lombok.Getter;
import lombok.Setter;
import xyz.migoo.framework.common.pojo.PageParam;

@Getter
@Setter
public class TagQueryReqVO extends PageParam {

    private Long projectId;

    private String name;

    private Integer status;
}
