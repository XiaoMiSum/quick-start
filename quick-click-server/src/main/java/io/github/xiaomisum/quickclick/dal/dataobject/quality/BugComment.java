package io.github.xiaomisum.quickclick.dal.dataobject.quality;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

@EqualsAndHashCode(callSuper = true)
@TableName(value = "qc_quality_bug_comment", autoResultMap = true)
@Data
public class BugComment extends BaseDO<Long> {

    /**
     * 缺陷编号
     */
    private String bugId;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 内容
     */
    private String content;
}
