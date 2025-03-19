/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021.  Lorem XiaoMiSum (mi_xiao@qq.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * 'Software'), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.github.xiaomisum.quickclick.model.dto;

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

        public TestcaseStepTypeHandler(Class<?> type) {
            super(type);
        }

        @Override
        public List<TestcaseStep> parse(String json) {
            return JsonUtils.parseArray(json, TestcaseStep.class);
        }

        @Override
        public String toJson(List<TestcaseStep> obj) {
            return JsonUtils.toJsonString(obj);
        }
    }
}
