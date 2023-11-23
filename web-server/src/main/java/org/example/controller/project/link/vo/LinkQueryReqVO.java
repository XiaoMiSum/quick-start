package org.example.controller.project.link.vo;

import lombok.Getter;
import lombok.Setter;
import xyz.migoo.framework.common.pojo.PageParam;
import xyz.migoo.framework.common.util.json.JsonUtils;

@Getter
@Setter
public class LinkQueryReqVO extends PageParam {

    private Long projectId;

    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }
}
