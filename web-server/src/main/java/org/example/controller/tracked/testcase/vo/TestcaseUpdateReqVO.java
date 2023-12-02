package org.example.controller.tracked.testcase.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestcaseUpdateReqVO extends TestcaseBaseVO {

    @NotNull(message = "用例编号不能为空")
    private Long id;

    private String reviewed;

    @NotNull(message = "执行步骤不能为空")
    private List<TestcaseStep> steps;
}
