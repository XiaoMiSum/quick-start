package io.github.xiaomisum.quickclick.controller.quality.review.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReviewCaseExecRecordAddReqVO extends ReviewCaseExecRecordBaseVO {

    /**
     * 项目编号
     */
    @NotBlank(message = "projectId 不能为空")
    private String projectId;

    /**
     * 评审编号
     */
    @NotBlank(message = "reviewId 不能为空")
    private String reviewId;

}
