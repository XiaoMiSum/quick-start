package io.github.xiaomisum.quickclick.controller.report.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class QualificationRateRespVO {

    private TitleRespVO title;

    private List<Item> first;

    private List<Item> items;


    @Data
    public static class Item {

        private Long projectId;

        private String projectName;

        private Long managerId;

        private String managerName;

        private BigDecimal qualificationRate;
    }
}