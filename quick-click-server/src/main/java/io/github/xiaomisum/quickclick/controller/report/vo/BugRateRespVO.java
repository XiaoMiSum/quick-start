package io.github.xiaomisum.quickclick.controller.report.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugRateRespVO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 测试用例总数
     */
    private Integer testcaseTotal;

    /**
     * 缺陷总数
     */
    private Integer bugTotal;
}
