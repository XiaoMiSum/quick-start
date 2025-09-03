package io.github.xiaomisum.quickclick.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * AI生成测试用例状态枚举
 */
@Getter
@AllArgsConstructor
public enum AiGeneratedTestcaseStatusEnum {

    /**
     * 待确认
     */
    PENDING(0, "待确认"),

    /**
     * 已确认
     */
    CONFIRMED(1, "已确认"),

    /**
     * 已拒绝
     */
    REJECTED(2, "已拒绝"),

    /**
     * 已导入
     */
    IMPORTED(3, "已导入");

    /**
     * 状态值
     */
    private final Integer status;

    /**
     * 状态描述
     */
    private final String description;
}