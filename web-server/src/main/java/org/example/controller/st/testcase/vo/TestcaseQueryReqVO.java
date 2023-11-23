package org.example.controller.st.testcase.vo;

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
    private String name;
    private Long moduleId;
    private String level;

    public TestcaseQueryReqVO(int pageNo, int pageSize) {
        setPageNo(pageNo);
        setPageSize(pageSize);
    }

    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }
}
