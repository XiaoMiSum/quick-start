package io.github.xiaomisum.quickclick.controller.report.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TitleRespVO {

    private final String x = "center";

    /**
     * echarts 标题
     */
    private String text;

    /**
     * echarts 副标题
     */
    private String subtext;
}
