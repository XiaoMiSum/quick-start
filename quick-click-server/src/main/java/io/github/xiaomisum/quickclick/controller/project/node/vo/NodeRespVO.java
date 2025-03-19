package io.github.xiaomisum.quickclick.controller.project.node.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeRespVO extends NodeBaseVO {

    private String id;

    private String path;
}
