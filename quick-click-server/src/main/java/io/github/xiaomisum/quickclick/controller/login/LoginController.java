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

package io.github.xiaomisum.quickclick.controller.login;

import io.github.xiaomisum.quickclick.controller.login.vo.AuthLoginReqVO;
import io.github.xiaomisum.quickclick.controller.login.vo.AuthLoginRespVO;
import io.github.xiaomisum.quickclick.controller.login.vo.AuthMenuRespVO;
import io.github.xiaomisum.quickclick.controller.login.vo.PasswordVO;
import io.github.xiaomisum.quickclick.convert.AuthConvert;
import io.github.xiaomisum.quickclick.dal.dataobject.project.Project;
import io.github.xiaomisum.quickclick.dal.dataobject.sys.Menu;
import io.github.xiaomisum.quickclick.dal.dataobject.sys.User;
import io.github.xiaomisum.quickclick.enums.MenuTypeEnum;
import io.github.xiaomisum.quickclick.service.login.TokenService;
import io.github.xiaomisum.quickclick.service.project.ProjectService;
import io.github.xiaomisum.quickclick.service.sys.permission.PermissionService;
import io.github.xiaomisum.quickclick.service.sys.user.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.migoo.framework.common.exception.util.ServiceExceptionUtil;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.common.util.collection.SetUtils;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.annotation.CurrentUser;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static io.github.xiaomisum.quickclick.enums.ErrorCodeConstants.USER_PASSWORD_OLD_NEW;
import static xyz.migoo.framework.common.enums.CommonStatusEnum.ENABLE;

@RestController
public class LoginController {

    @Resource
    private TokenService tokenService;
    @Resource
    private UserService userService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private ProjectService projectService;

    @PostMapping("/sign-in")
    public Result<AuthLoginRespVO> login(@RequestBody AuthLoginReqVO req) {
        return Result.getSuccessful(tokenService.signIn(req));
    }

    @GetMapping("/user-info")
    public Result<?> getUserInfo(@CurrentUser LoginUser loginUser) {
        User user = userService.get(loginUser.getId());
        Set<Long> roleIds = permissionService.getUserRoleIds(user.getId(), SetUtils.asSet(ENABLE.getStatus()));
        List<Menu> menuList = permissionService.getRoleMenusFromCache(
                roleIds,
                SetUtils.asSet(MenuTypeEnum.DIR.getType(), MenuTypeEnum.MENU.getType(), MenuTypeEnum.BUTTON.getType()),
                SetUtils.asSet(ENABLE.getStatus()),
                true);
        if (Objects.isNull(user.getLastProject())) {
            user.setLastProject(Optional.ofNullable(projectService.getFirstProject()).orElse(new Project()).getId());
        }
        return Result.getSuccessful(AuthConvert.INSTANCE.convert(user, menuList));
    }

    @GetMapping("user-menus")
    public Result<List<AuthMenuRespVO>> getMenus(@CurrentUser LoginUser loginUser) {
        Set<Long> roleIds = permissionService.getUserRoleIds(loginUser.getId(), SetUtils.asSet(ENABLE.getStatus()));
        // 获得用户拥有的菜单列表
        List<Menu> menuList = permissionService.getRoleMenusFromCache(
                roleIds,
                SetUtils.asSet(MenuTypeEnum.DIR.getType(), MenuTypeEnum.MENU.getType()),
                SetUtils.asSet(ENABLE.getStatus()),
                true);
        // 转换成 Tree 结构返回
        return Result.getSuccessful(AuthConvert.INSTANCE.convert(menuList));
    }

    @PostMapping("/password")
    public Result<?> updatePassword(@CurrentUser LoginUser user, @RequestBody PasswordVO password) {
        if (Objects.equals(password.getNewPassword(), password.getOldPassword())) {
            throw ServiceExceptionUtil.get(USER_PASSWORD_OLD_NEW);
        }
        password.setId(user.getId());
        userService.update(password);
        return Result.getSuccessful();
    }
}
