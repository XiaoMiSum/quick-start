package io.github.xiaomisum.quickclick.controller.report.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugRateDetailRespVO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 项目编号
     */
    private String projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 测试用例总数
     */
    private Integer testcaseTotal;

    /**
     * 缺陷总数
     */
    private Integer bugTotal;
}
