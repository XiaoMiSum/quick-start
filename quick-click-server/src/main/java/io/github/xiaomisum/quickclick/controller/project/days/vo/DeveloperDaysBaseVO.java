package io.github.xiaomisum.quickclick.controller.project.days.vo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeveloperDaysBaseVO {

    /**
     * 编号
     */
    @NotNull(message = "id 不能为空")
    private Long id;

    /**
     * 归属测试用例数量
     */
    @NotNull(message = "testcaseTotal 不能为空")
    @Min(value = 0, message = "testcaseTotal 不能小于0")
    private Integer testcaseTotal;

    /**
     * 归属缺陷数
     */
    @NotNull(message = "newBugTotal 不能为空")
    @Min(value = 0, message = "newBugTotal 不能小于0")
    private Integer newBugTotal;

    /**
     * 修复缺陷数
     */
    @NotNull(message = "closedBugTotal 不能为空")
    @Min(value = 0, message = "closedBugTotal 不能小于0")
    private Integer closedBugTotal;

    /**
     * 修复缺陷耗时
     */
    @NotNull(message = "fixedBugDuration 不能为空")
    @Min(value = 0, message = "fixedBugDuration 不能小于0")
    private Integer fixedBugDuration;

    /**
     * 归属缺陷激活数
     */
    @NotNull(message = "reopenedBugTotal 不能为空")
    @Min(value = 0, message = "reopenedBugTotal 不能小于0")
    private Integer reopenedBugTotal;
}
