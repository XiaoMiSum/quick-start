package io.github.xiaomisum.quickclick.controller.quality.bug.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugRespVO extends BugBaseVO {

    private String id;

    /**
     * 指派时间
     */
    private LocalDateTime assignedTime;

    /**
     * 确认时间
     */
    private LocalDateTime confirmedTime;

    /**
     * 修复人
     */
    private Long fixer;

    /**
     * 修复时间
     */
    private LocalDateTime fixedTime;

    /**
     * 关闭人
     */
    private Long closer;

    /**
     * 关闭时间
     */
    private LocalDateTime closedTime;

    /**
     * 产生原因
     */
    private String cause;

    /**
     * 产生原因详细描述
     */
    private String rootCause;

    /**
     * 解决方案
     */
    private String solution;

    /**
     * 解决方案详细描述
     */
    private String resolution;

    /**
     * 激活次数
     */
    private Integer reopenedTimes;

    /**
     * 来源
     */
    private String source;
}
