package io.github.xiaomisum.quickclick.controller.quality.bug.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BugReopenReqVO {

    /**
     * 编号
     */
    @NotBlank(message = "handler 不能为空")
    private String id;

    /**
     * 处理人id
     */
    @NotNull(message = "handler 不能为空")
    private Long handler;
}
