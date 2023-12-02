package org.example.controller.tracked.testcase.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import cn.afterturn.easypoi.handler.inter.IExcelDictHandler;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dal.dataobject.project.ProjectModule;
import org.example.dal.dataobject.project.Tag;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelTarget("Testcase")
public class TestcaseExportVO {

    @Excel(name = "标题", needMerge = true, width = 25)
    private String name;

    @Excel(name = "所属模块", needMerge = true, width = 20, dict = "module", addressList = true)
    private Long moduleId;

    @Excel(name = "用例等级", needMerge = true, dict = "level", addressList = true)
    private String level;

    @Excel(name = "前置条件", needMerge = true, width = 20)
    private String precondition;

    @ExcelCollection(name = "执行步骤")
    private List<TestcaseStep> steps;


    public static class TestcaseExcelDictHandler implements IExcelDictHandler {

        private final Map<String, Map<Object, String>> dictMap = new HashMap<>();

        public TestcaseExcelDictHandler() {
            Map<Object, String> maps = Maps.newHashMap();
            for (int i = 0; i < 4; i++) {
                String item = "P" + i;
                maps.put(item, item);
            }
            dictMap.put("level", maps);
        }

        public void add(String dict, List<?> values) {
            Map<Object, String> map = Maps.newHashMap();
            values.forEach(item -> {
                if (item instanceof Tag tag) {
                    map.put("没有可选项，请在数据验证中添加", "没有可选项，请在数据验证中添加");
                    map.put(tag.getName(), tag.getName());
                } else if (item instanceof ProjectModule module) {
                    map.put(module.getId(), module.getPath());
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
            return "-1"; // 当没有对应字典值时，返回原数据
        }
    }
}
