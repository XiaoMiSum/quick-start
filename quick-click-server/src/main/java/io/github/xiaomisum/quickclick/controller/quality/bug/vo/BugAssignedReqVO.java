package io.github.xiaomisum.quickclick.controller.quality.bug.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugAssignedReqVO {

    /**
     * 编号
     */
    @NotNull(message = "id 不能为空")
    private List<String> ids;
    /**
     * 处理人
     */
    @NotNull(message = "handler 不能为空")
    private Long handler;

}
