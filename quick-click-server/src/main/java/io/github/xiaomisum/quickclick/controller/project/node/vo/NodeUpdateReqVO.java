package io.github.xiaomisum.quickclick.controller.project.node.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeUpdateReqVO extends NodeBaseVO {

    /**
     * 节点编号
     */
    @NotBlank(message = "id 不能为空")
    private String id;
}
