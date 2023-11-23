package org.example.controller.st.plan.vo;

import lombok.Getter;
import lombok.Setter;
import xyz.migoo.framework.common.pojo.PageParam;
import xyz.migoo.framework.common.util.json.JsonUtils;

@Getter
@Setter
public class PlanCaseQueryReqVO extends PageParam {

    private Long planId;

    private Long moduleId;

    private String caseName;

    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }
}
