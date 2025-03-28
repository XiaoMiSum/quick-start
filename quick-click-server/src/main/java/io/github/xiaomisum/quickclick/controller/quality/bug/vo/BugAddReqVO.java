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
public class BugAddReqVO extends BugBaseVO {

    /**
     * 指派时间
     */
    private LocalDateTime assignedTime = LocalDateTime.now();

    /**
     * 创建人id
     */
    private Long creatorId;

    private String content;
    
}
