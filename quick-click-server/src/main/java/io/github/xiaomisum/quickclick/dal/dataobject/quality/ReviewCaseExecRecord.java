package io.github.xiaomisum.quickclick.dal.dataobject.quality;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.xiaomisum.quickclick.enums.TestStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "qc_quality_test_review_exec_record", autoResultMap = true)
@Data
public class ReviewCaseExecRecord extends BaseDO<Long> {

    /**
     * 项目编号
     */
    private String projectId;

    /**
     * 评审编号
     */
    private String reviewId;

    /**
     * 关联编号
     */
    private Long dataId;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 操作
     */
    private TestStatus operation;

    /**
     * 内容
     */
    private String content;
}