package io.github.xiaomisum.quickclick.dal.dataobject.report;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

/**
 * 开发人员每日数据记录
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "qc_report_quality_tester_day", autoResultMap = true)
@Data
public class TesterBasicData extends BaseDO<Long> {

    /**
     * 日期
     */
    private String date;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 项目编号
     */
    private String projectId;

    /**
     * 新增测试用例数量
     */
    private Integer testcaseTotal;

    /**
     * 执行测试用例数量
     */
    private Integer executeTestcaseTotal;

    /**
     * 新增缺陷数
     */
    private Integer newBugTotal;

    /**
     * 关闭缺陷数
     */
    private Integer closedBugTotal;

    /**
     * 激活缺陷数
     */
    private Integer reopenedBugTotal;
}
