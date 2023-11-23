package org.example.controller.project.tag.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TagUpdateReqVo extends TagBaseVO {

    @NotNull(message = "标签编号不能为空")
    private Long id;
}
