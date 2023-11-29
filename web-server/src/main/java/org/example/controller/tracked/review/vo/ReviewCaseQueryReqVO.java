package org.example.controller.tracked.review.vo;

import lombok.Getter;
import lombok.Setter;
import xyz.migoo.framework.common.pojo.PageParam;
import xyz.migoo.framework.common.util.json.JsonUtils;

@Getter
@Setter
public class ReviewCaseQueryReqVO extends PageParam {

    private Long reviewId;

    private Long moduleId;

    private String caseName;

    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }
}
