package org.example.controller.login.vo;

import lombok.Data;
import xyz.migoo.framework.common.util.json.JsonUtils;

@Data
public class AuthLoginRespVO {

    private String token;

    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }

}