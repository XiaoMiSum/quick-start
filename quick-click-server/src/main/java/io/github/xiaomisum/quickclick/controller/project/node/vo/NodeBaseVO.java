package io.github.xiaomisum.quickclick.controller.project.node.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeBaseVO {

    /**
     * 项目编号
     */
    @NotBlank(message = "projectId 不能为空")
    private String projectId;
    
    /**
     * 父节点编号
     */
    private String parentId;

    @NotBlank(message = "title 不能为空")
    @Size(max = 10, message = "title 最大长度 10")
    private String title;

    private Integer sort;
}
