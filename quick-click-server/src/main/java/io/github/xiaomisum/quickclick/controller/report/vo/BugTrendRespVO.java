package io.github.xiaomisum.quickclick.controller.report.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class BugTrendRespVO {

    private TitleRespVO title;

    private List<Item> items;

    @Data
    public static class Item {

        @DateTimeFormat(pattern = "yyyy-MM")
        @JsonFormat(pattern = "yyyy-MM")
        private LocalDate month;

        private Integer total;
    }
}
