package org.example.controller.login.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import xyz.migoo.framework.common.util.json.JsonUtils;
import xyz.migoo.framework.security.core.LoginUser;

@Data
public class AuthLoginReqVO {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "登录密码不能为空")
    private String password;

    private String code;

    private String uuid;

    private LoginUser.Client client;

    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }
}
