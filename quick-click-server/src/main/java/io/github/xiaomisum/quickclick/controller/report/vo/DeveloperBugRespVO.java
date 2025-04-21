package io.github.xiaomisum.quickclick.controller.report.vo;


import lombok.Data;

import java.util.List;

@Data
public class DeveloperBugRespVO {

    private TitleRespVO title;

    private List<Item> first;

    private List<Item> items;

    @Data
    public static class Item {

        private Long userId;

        private String name;

        private Integer total;
    }

}
