package io.github.xiaomisum.quickclick.enums;

import xyz.migoo.framework.common.exception.ErrorCode;

public class ErrorCodeConstants {
    public static final ErrorCode NODE_HAS_CHILDREN = ErrorCode.of(31001001, "所选模块存在子模块，不能删除");
    public static final ErrorCode NODE_HAS_CASE = ErrorCode.of(31001002, "所选模块存在用例，不能删除");
    public static final ErrorCode ORIGINAL_CASE_NOT_EXIST = ErrorCode.of(32001001, "原始用例不存在");
    public static final ErrorCode TEST_CASE_IMPORT_ERROR = ErrorCode.of(35001001, "测试用例导入失败");
    public static final ErrorCode TEST_CASE_BATCH_UPDATE_ERROR = ErrorCode.of(35001002, "模块、优先级、责任人不能同时为空");

    static {
        ErrorCode.put(ErrorCodeConstants.class);
    }

    public ErrorCodeConstants() {
    }
}