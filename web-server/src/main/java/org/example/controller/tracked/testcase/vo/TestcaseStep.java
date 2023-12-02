package org.example.controller.tracked.testcase.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.migoo.framework.common.util.json.JsonUtils;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelTarget("TestcaseStep")
public class TestcaseStep {

    @Excel(name = "步骤描述", width = 40)
    private String exec;

    @Excel(name = "期望结果", width = 40)
    private String expected;

    @Excel(name = "实际结果")
    private String actual;


    public static class TestcaseStepTypeHandler extends AbstractJsonTypeHandler<List<TestcaseStep>> {

        @Override
        protected List<TestcaseStep> parse(String json) {
            return JsonUtils.parseArray(json, TestcaseStep.class);
        }

        @Override
        protected String toJson(List<TestcaseStep> obj) {
            return JsonUtils.toJsonString(obj);
        }
    }
}
