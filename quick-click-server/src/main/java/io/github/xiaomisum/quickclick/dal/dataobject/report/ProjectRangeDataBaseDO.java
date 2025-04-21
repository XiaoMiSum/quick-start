package io.github.xiaomisum.quickclick.dal.dataobject.report;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectRangeDataBaseDO extends BaseDO<Long> {

    /**
     * 区间开始日期
     */
    private LocalDate startDate;

    /**
     * 区间结束日期
     */
    private LocalDate endDate;

    /**
     * 项目编号
     */
    private String projectId;

    /**
     * 项目经理
     */
    private Long managerId;

    /**
     * 本期执行用例
     */
    private Integer thisRangeTestcaseTotal;

    /**
     * 质量合格率
     */
    private BigDecimal qualificationRate;

    /**
     * 本期新增缺陷
     */
    private Integer thisRangeBugTotal;

    /**
     * 上期遗留缺陷
     */
    private Integer lastRangeBugTotal;


    /**
     * 本期关闭缺陷
     */
    private Integer thisRangeClosedBugTotal;

    /**
     * Bug 解决人
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Long> unfinishedBugHandler;

    /**
     * 计划完成日期
     */
    private LocalDate completionDate;
}
