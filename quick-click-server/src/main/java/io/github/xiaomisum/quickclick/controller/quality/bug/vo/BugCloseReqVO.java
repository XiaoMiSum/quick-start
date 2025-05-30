package io.github.xiaomisum.quickclick.controller.quality.bug.vo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BugCloseReqVO {

    /**
     * 缺陷编号
     */
    @NotBlank(message = "id 不能为空")
    private String id;

    /**
     * 评论信息
     */
    private String comment;

    /**
     * 耗时
     */
    @NotNull(message = "duration 不能为空")
    @Min(value = 1, message = "duration 最小为1")
    private Integer duration;
}
