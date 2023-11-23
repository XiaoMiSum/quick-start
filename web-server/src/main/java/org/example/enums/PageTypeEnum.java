package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PageTypeEnum {

    MEMBER(1),
    MANAGER(2);

    /**
     * 类型
     */
    private final Integer type;
}
