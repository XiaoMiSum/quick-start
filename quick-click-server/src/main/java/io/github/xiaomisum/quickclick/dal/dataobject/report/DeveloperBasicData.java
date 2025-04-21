package io.github.xiaomisum.quickclick.dal.dataobject.report;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

/**
 * 开发人员每日数据记录
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "qc_report_quality_developer_day", autoResultMap = true)
@Data
public class DeveloperBasicData extends BaseDO<Long> {

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
     * 归属测试用例数量
     */
    private Integer testcaseTotal;

    /**
     * 新增缺陷数
     */
    private Integer newBugTotal;

    /**
     * 关闭缺陷数
     */
    private Integer closedBugTotal;

    /**
     * 修复缺陷次数
     */
    private Integer fixedBugTotal;

    /**
     * 修复缺陷耗时
     */
    private Integer fixedBugDuration;

    /**
     * 归属缺陷激活数
     */
    private Integer reopenedBugTotal;
}
