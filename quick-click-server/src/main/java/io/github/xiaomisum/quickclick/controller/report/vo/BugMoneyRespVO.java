package io.github.xiaomisum.quickclick.controller.report.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugMoneyRespVO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 次数
     */
    private Integer total;

    /**
     * 时间
     */
    private Integer duration;
}
