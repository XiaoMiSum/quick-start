package io.github.xiaomisum.quickclick.dal.dataobject.quality;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.xiaomisum.quickclick.enums.BugStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "qc_quality_bug_unclosed_record", autoResultMap = true)
@Data
public class BugUnclosedRecord extends BaseDO<Long> {

    /**
     * 缺陷编号
     */
    private String bugId;

    /**
     * 项目编号
     */
    private String projectId;

    /**
     * 创建日期
     */
    private LocalDate createDate;

    /**
     * 缺陷状态
     */
    private BugStatus status;

    /**
     * 责任人
     */
    private Long supervisor;

    /**
     * 修复人
     */
    private Long fixer;
}
