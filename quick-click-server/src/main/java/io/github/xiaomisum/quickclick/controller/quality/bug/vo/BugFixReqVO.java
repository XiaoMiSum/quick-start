package io.github.xiaomisum.quickclick.controller.quality.bug.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BugFixReqVO {

    /**
     * 缺陷编号
     */
    @NotBlank(message = "id 不能为空")
    private String id;

    /**
     * 产生原因
     */
    @NotBlank(message = "cause 不能为空")
    private String cause;

    /**
     * 产生原因详细描述
     */
    @NotBlank(message = "rootCause 不能为空")
    private String rootCause;

    /**
     * 解决方案
     */
    @NotBlank(message = "solution 不能为空")
    private String solution;

    /**
     * 解决方案详细描述
     */
    @NotBlank(message = "resolution 不能为空")
    private String resolution;
}
