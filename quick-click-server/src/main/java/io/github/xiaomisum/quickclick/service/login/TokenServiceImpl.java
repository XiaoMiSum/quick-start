/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021.  Lorem XiaoMiSum (mi_xiao@qq.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * 'Software'), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.github.xiaomisum.quickclick.service.login;

import io.github.xiaomisum.quickclick.enums.ErrorCodeConstants;
import io.github.xiaomisum.quickclick.service.sys.permission.PermissionService;
import io.github.xiaomisum.quickclick.service.sys.user.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import io.github.xiaomisum.quickclick.controller.login.vo.AuthLoginReqVO;
import io.github.xiaomisum.quickclick.controller.login.vo.AuthLoginRespVO;
import io.github.xiaomisum.quickclick.convert.AuthConvert;
import io.github.xiaomisum.quickclick.dal.dataobject.sys.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.enums.CommonStatusEnum;
import xyz.migoo.framework.common.exception.util.ServiceExceptionUtil;
import xyz.migoo.framework.common.util.json.JsonUtils;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.service.SecuritySessionAuthService;

import java.util.Objects;

import static xyz.migoo.framework.common.enums.NumberConstants.N_2;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    @Lazy
    private AuthenticationManager authenticationManager;

    @Resource
    private SecuritySessionAuthService securitySessionAuthService;

    @Resource
    private UserService userService;

    @Resource
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String jsonUser) {
        AuthLoginReqVO req = JsonUtils.parseObject(jsonUser, AuthLoginReqVO.class);
        User user = userService.get(req.getUsername());
        return AuthConvert.INSTANCE.convert(user, req);
    }

    @Override
    public LoginUser verifyTokenAndRefresh(String token) {
        // 获得 LoginUser
        LoginUser loginUser = securitySessionAuthService.getLoginUser(token);
        if (Objects.nonNull(loginUser)) {
            // 刷新 LoginUser 缓存
            this.refreshLoginUserCache(token, loginUser);
        }
        return loginUser;
    }

    @Override
    public void signOut(String token) {
        securitySessionAuthService.deleteUserSession(token);
    }

    @Override
    public AuthLoginRespVO signIn(AuthLoginReqVO req) {
        LoginUser loginUser = login0(JsonUtils.toJsonString(req), req.getPassword());
        // 缓存登陆用户到 Redis 中，返回 sessionId 编号
        String token = securitySessionAuthService.createUserSession(loginUser);
        return new AuthLoginRespVO().setToken(token);
    }

    private LoginUser login0(String reqJson, String password) {
        try {
            // 调用 Spring Security 的 AuthenticationManager#authenticate(...) 方法，使用账号密码进行认证
            // 在其内部，会调用到 loadUserByUsername 方法，获取 User 信息
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(reqJson, password));
            return (LoginUser) authentication.getPrincipal();
        } catch (BadCredentialsException badCredentialsException) {
            throw ServiceExceptionUtil.get(ErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS);
        } catch (DisabledException disabledException) {
            throw ServiceExceptionUtil.get(ErrorCodeConstants.AUTH_LOGIN_USER_DISABLED);
        } catch (AuthenticationException authenticationException) {
            log.error("[login0][user({}) 发生未知异常]", reqJson, authenticationException);
            throw ServiceExceptionUtil.get(ErrorCodeConstants.AUTH_LOGIN_FAIL_UNKNOWN);
        }
    }

    private void refreshLoginUserCache(String token, LoginUser loginUser) {
        // 每 1/2 的 Session 超时时间，刷新 LoginUser 缓存
        if (System.currentTimeMillis() - loginUser.getUpdateTime().getTime() > securitySessionAuthService.getSessionTimeoutMillis() / N_2) {
            // 获取用户为null 或者 被禁用 认为 token 过期，方便前端跳转到登录界面
            User user = userService.get(loginUser.getUsername());
            if (Objects.isNull(user) || Objects.equals(user.getStatus(), CommonStatusEnum.DISABLE.getStatus())) {
                throw ServiceExceptionUtil.get(ErrorCodeConstants.AUTH_TOKEN_EXPIRED);
            }
            // 刷新 LoginUser 缓存
            securitySessionAuthService.refreshUserSession(token, loginUser);
        }
    }
}
