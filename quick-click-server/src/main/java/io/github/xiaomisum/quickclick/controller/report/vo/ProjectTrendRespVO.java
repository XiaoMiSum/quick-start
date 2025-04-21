package io.github.xiaomisum.quickclick.controller.report.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static xyz.migoo.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

@Data
public class ProjectTrendRespVO {

    private TitleRespVO title;

    private List<Item> items;

    @Data
    public static class Item {

        @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
        @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY)
        private LocalDate start;

        @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
        @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY)
        private LocalDate end;

        private BigDecimal qualificationRate;
    }
}
