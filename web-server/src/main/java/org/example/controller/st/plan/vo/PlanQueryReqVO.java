package org.example.controller.st.plan.vo;

import lombok.Getter;
import lombok.Setter;
import xyz.migoo.framework.common.pojo.PageParam;
import xyz.migoo.framework.common.util.json.JsonUtils;

@Getter
@Setter
public class PlanQueryReqVO extends PageParam {

    private Long projectId;

    private String name;

    private String result;

    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }
}
