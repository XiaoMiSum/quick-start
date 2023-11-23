package org.example.service.login;

import jakarta.validation.Valid;
import org.example.controller.login.vo.AuthLoginReqVO;
import org.example.controller.login.vo.AuthLoginRespVO;
import xyz.migoo.framework.security.core.service.SecurityAuthFrameworkService;


public interface TokenService extends SecurityAuthFrameworkService {

    /**
     * 账号登录
     *
     * @param reqVO 登录信息
     * @return 身份令牌，使用 JWT 方式
     */
    AuthLoginRespVO signIn(@Valid AuthLoginReqVO reqVO);
}
