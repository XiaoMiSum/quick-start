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

package io.github.xiaomisum.quickclick.controller.quality.testcase.vo.testcase;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import cn.afterturn.easypoi.handler.inter.IExcelDictHandler;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import io.github.xiaomisum.quickclick.dal.dataobject.project.ProjectNode;
import io.github.xiaomisum.quickclick.model.dto.CaseStep;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelTarget("Testcase")
public class TestcaseExportVO {

    @Excel(name = "标题", needMerge = true, width = 25)
    private String title;

    @Excel(name = "所属模块", needMerge = true, width = 20, dict = "node", addressList = true)
    private String nodeId;

    @Excel(name = "用例等级", needMerge = true, dict = "priority", addressList = true)
    private String priority;

    @Excel(name = "标签", needMerge = true, width = 20)
    private String tags;

    @Excel(name = "前置条件", needMerge = true, width = 20)
    private String prerequisite;

    @ExcelCollection(name = "执行步骤")
    private List<CaseStep> steps;


    public static class TestcaseExcelDictHandler implements IExcelDictHandler {

        private final Map<String, Map<Object, String>> dictMap = new HashMap<>();

        public TestcaseExcelDictHandler() {
            Map<Object, String> maps = Maps.newHashMap();
            for (int i = 0; i < 4; i++) {
                String item = "P" + i;
                maps.put(item, item);
            }
            dictMap.put("priority", maps);
        }

        public void add(String dict, List<?> values) {
            Map<Object, String> map = Maps.newHashMap();
            values.forEach(item -> {
                if (item instanceof ProjectNode node) {
                    map.put(node.getId(), node.getPath());
                }
            });

            dictMap.put(dict, map);
        }

        @Override
        public List<Map> getList(String typeCode) {
            List<Map> result = new ArrayList<>();
            Map<Object, String> biMap = Optional.ofNullable(dictMap.get(typeCode)).orElse(Maps.newHashMap());
            for (Map.Entry<Object, String> entry : biMap.entrySet()) {
                Map<Object, Object> map = new HashMap<>();
                map.put("dictKey", entry.getKey());
                map.put("dictValue", entry.getValue());
                result.add(map);
            }
            return result;
        }


        @Override
        public String toName(String dict, Object obj, String name, Object value) {
            if (StrUtil.isBlankIfStr(value)) {
                return null;
            }
            // 获取对应的字典
            Map<?, String> biMap = Optional.ofNullable(dictMap.get(dict)).orElse(Maps.newHashMap());
            if (biMap.isEmpty()) {
                return value.toString(); // 当没有对应字典Map时，返回原数据
            }
            String result = biMap.get(value);
            if (StrUtil.isNotEmpty(result)) {
                return result;
            } else {
                return value.toString(); // 当没有对应字典值时，返回原数据
            }
        }

        @Override
        public String toValue(String dict, Object obj, String name, Object value) {
            if (StrUtil.isBlankIfStr(name)) {
                return null;
            }
            // 获取对应的字典
            Map<Object, String> biMap = dictMap.get(dict);
            if (biMap.isEmpty()) {
                return value.toString(); // 当没有对应字典Map时，返回原数据
            }
            for (Map.Entry<Object, String> entry : biMap.entrySet()) {
                if (Objects.equals(entry.getValue(), value)) {
                    return entry.getKey().toString();
                }
            }
            return ""; // 当没有对应字典值时，返回原数据
        }
    }
}
