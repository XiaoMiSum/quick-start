package io.github.xiaomisum.quickclick.controller.report.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

import static xyz.migoo.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

@Data
public class ProjectBugRespVO {

    /**
     * 项目编号
     */
    private Long projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目经理 userid
     */
    private Long managerId;

    /**
     * 项目经理 姓名
     */
    private String managerName;

    /**
     * 缺陷总数
     */
    private Integer bugTotal;

    /**
     * 本期新增缺陷
     */
    private Integer thisRangeBugTotal;

    /**
     * 上期遗留缺陷
     */
    private Integer lastRangeBugTotal;

    /**
     * 本期未关闭缺陷
     */
    private Integer thisRangeUnclosedBugTotal;
    /**
     * 本期关闭缺陷
     */
    private Integer thisRangeClosedBugTotal;

    /**
     * 处理人
     */
    private List<Long> unfinishedBugHandler;

    /**
     * 计划完成日期
     */
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDate completionDate;
}
