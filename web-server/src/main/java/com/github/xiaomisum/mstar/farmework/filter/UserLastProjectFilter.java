package com.github.xiaomisum.mstar.farmework.filter;

import com.github.xiaomisum.mstar.dal.dataobject.sys.User;
import com.github.xiaomisum.mstar.service.sys.user.UserService;
import jakarta.servlet.*;
import xyz.migoo.framework.common.util.servlet.ServletUtils;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.util.SecurityFrameworkUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class UserLastProjectFilter implements Filter {

    private final UserService userService;

    public UserLastProjectFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LoginUser user = SecurityFrameworkUtils.getLoginUser();
        if (Objects.nonNull(user)) {
            Map<String, String> headers = ServletUtils.getHeaders(ServletUtils.getRequest());
            userService.update(new User().setLastProject(headers.get("x-project-id")).setId(user.getId()));
        }
        chain.doFilter(request, response);
    }
}
