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

package io.github.xiaomisum.quickclick.farmework.filter;

import io.github.xiaomisum.quickclick.dal.dataobject.sys.User;
import io.github.xiaomisum.quickclick.service.sys.user.UserService;
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
