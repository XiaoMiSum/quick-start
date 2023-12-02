package org.example.controller.tracked.testcase.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.migoo.framework.common.pojo.PageParam;
import xyz.migoo.framework.common.util.json.JsonUtils;

@Getter
@Setter
@NoArgsConstructor
public class TestcaseQueryReqVO extends PageParam {

    private Long projectId;
    private Long moduleId;
    private String name;
    private String level;
    private String tag;
    private String reviewed;

    public TestcaseQueryReqVO(int pageNo, int pageSize) {
        setPageNo(pageNo);
        setPageSize(pageSize);
    }

    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }
}
