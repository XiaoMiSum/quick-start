package org.example.controller.tracked.review.vo;

import lombok.Getter;
import lombok.Setter;
import xyz.migoo.framework.common.pojo.PageParam;
import xyz.migoo.framework.common.util.json.JsonUtils;

@Getter
@Setter
public class ReviewQueryReqVO extends PageParam {

    private Long projectId;

    private String name;

    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }
}
