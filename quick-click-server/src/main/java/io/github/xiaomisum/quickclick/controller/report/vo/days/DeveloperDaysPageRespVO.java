package io.github.xiaomisum.quickclick.controller.report.vo.days;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeveloperDaysPageRespVO extends DeveloperDaysBaseVO {

    /**
     * 日期
     */
    private String date;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 姓名
     */
    private String name;
}
