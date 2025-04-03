package io.github.xiaomisum.quickclick.controller.quality.bug.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BugRejectedReqVO {

    /**
     * 缺陷编号
     */
    @NotBlank(message = "id 不能为空")
    private String id;

    /**
     * 拒绝原因
     */
    @NotBlank(message = "rejectedCause 不能为空")
    private String rejectedCause;

    /**
     * 拒绝人
     */
    private Long rejectedUser;

    /**
     * 指派处理人
     */
    @NotNull(message = "handler 不能为空")
    private Long handler;

}
