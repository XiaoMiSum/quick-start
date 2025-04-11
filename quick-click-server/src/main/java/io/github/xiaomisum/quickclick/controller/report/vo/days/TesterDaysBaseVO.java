package io.github.xiaomisum.quickclick.controller.report.vo.days;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TesterDaysBaseVO {

    /**
     * 编号
     */
    @NotNull(message = "id 不能为空")
    private Long id;

    /**
     * 编写测试用例数
     */
    @NotNull(message = "testcaseTotal 不能为空")
    @Min(value = 0, message = "testcaseTotal 不能小于0")
    private Integer testcaseTotal;

    /**
     * 执行测试用例数
     */
    @NotNull(message = "executeTestcaseTotal 不能为空")
    @Min(value = 0, message = "executeTestcaseTotal 不能小于0")
    private Integer executeTestcaseTotal;

    /**
     * 新增缺陷数
     */
    @NotNull(message = "newBugTotal 不能为空")
    @Min(value = 0, message = "newBugTotal 不能小于0")
    private Integer newBugTotal;

    /**
     * 关闭缺陷数
     */
    @NotNull(message = "closedBugTotal 不能为空")
    @Min(value = 0, message = "closedBugTotal 不能小于0")
    private Integer closedBugTotal;

    /**
     * 归属缺陷激活数
     */
    @NotNull(message = "reopenedBugTotal 不能为空")
    @Min(value = 0, message = "reopenedBugTotal 不能小于0")
    private Integer reopenedBugTotal;
}
