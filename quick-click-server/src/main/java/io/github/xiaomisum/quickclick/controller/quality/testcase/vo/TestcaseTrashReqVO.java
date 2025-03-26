package io.github.xiaomisum.quickclick.controller.quality.testcase.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.migoo.framework.common.util.collection.CollectionUtils;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestcaseTrashReqVO {

    private List<String> ids;

    private String idListString;

    @NotBlank(message = "projectId 不能为空")
    private String projectId;

    public List<String> getIds() {
        return CollectionUtils.isAnyEmpty(ids) ? Arrays.stream(idListString.split(",")).toList() : ids;
    }
}
