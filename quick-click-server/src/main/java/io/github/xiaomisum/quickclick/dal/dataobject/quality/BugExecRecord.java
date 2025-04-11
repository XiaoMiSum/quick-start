package io.github.xiaomisum.quickclick.dal.dataobject.quality;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.xiaomisum.quickclick.enums.BugStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "qc_quality_bug_exec_record", autoResultMap = true)
@Data
public class BugExecRecord extends BaseDO<Long> {

    /**
     * 缺陷编号
     */
    private String bugId;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 操作
     */
    private BugStatus operation;

    /**
     * 内容
     */
    private String content;
}
