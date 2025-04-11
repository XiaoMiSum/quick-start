package io.github.xiaomisum.quickclick.controller.quality.review.vo;

import lombok.Data;

@Data
public class ReviewCaseExecRecordBaseVO {

    /**
     * 关联编号
     */
    private Long dataId;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 内容
     */
    private String content;
}
