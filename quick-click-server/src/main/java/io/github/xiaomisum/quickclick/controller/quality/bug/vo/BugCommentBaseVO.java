package io.github.xiaomisum.quickclick.controller.quality.bug.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BugCommentBaseVO {

    /**
     * 缺陷编号
     */
    @NotBlank(message = "bugId 不能为空")
    private String bugId;

    /**
     * 内容
     */
    @NotBlank(message = "bugId 不能为空")
    private String content;
}
