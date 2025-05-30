package io.github.xiaomisum.quickclick.controller.quality.bug.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BugExecRecordAddReqVO extends BugExecRecordBaseVO {

    /**
     * 用户编号
     */
    private Long userId;

}
