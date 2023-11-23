package org.example.controller.st.testcase.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestcaseAddReqVO extends TestcaseBaseVO {

    @NotNull(message = "执行步骤不能为空")
    private List<TestcaseStep> steps;
}
