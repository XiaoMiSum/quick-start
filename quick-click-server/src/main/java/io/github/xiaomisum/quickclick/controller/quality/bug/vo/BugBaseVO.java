package io.github.xiaomisum.quickclick.controller.quality.bug.vo;

import io.github.xiaomisum.quickclick.enums.BugStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BugBaseVO {

    /**
     * 标题
     */
    @NotBlank(message = "title 不能为空")
    private String title;

    /**
     * 项目编号
     */
    @NotBlank(message = "projectId 不能为空")
    private String projectId;

    /**
     * 模块编号
     */
    @NotBlank(message = "nodeId 不能为空")
    private String nodeId;

    /**
     * 严重程度
     */
    @NotBlank(message = "severity 不能为空")
    private String severity;

    /**
     * 优先级
     */
    @NotBlank(message = "priority 不能为空")
    private String priority;

    /**
     * 责任人
     */
    @NotNull(message = "supervisor 不能为空")
    private Long supervisor;

    /**
     * 处理人
     */
    @NotNull(message = "handler 不能为空")
    private Long handler;

    /**
     * 状态
     */
    @NotNull(message = "status 不能为空")
    private BugStatus status;
}
