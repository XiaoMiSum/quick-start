package org.example.controller.login;

import jakarta.annotation.Resource;
import org.example.controller.login.vo.AuthLoginReqVO;
import org.example.controller.login.vo.AuthLoginRespVO;
import org.example.controller.login.vo.AuthMenuRespVO;
import org.example.controller.login.vo.PasswordVO;
import org.example.convert.AuthConvert;
import org.example.dal.dataobject.sys.Menu;
import org.example.dal.dataobject.sys.User;
import org.example.enums.MenuTypeEnum;
import org.example.service.login.TokenService;
import org.example.service.sys.permission.PermissionService;
import org.example.service.sys.user.UserService;
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
import java.util.Set;

import static org.example.enums.ErrorCodeConstants.USER_PASSWORD_OLD_NEW;
import static xyz.migoo.framework.common.enums.CommonStatusEnum.ENABLE;

@RestController
public class LoginController {

    @Resource
    private TokenService tokenService;
    @Resource
    private UserService userService;
    @Resource
    private PermissionService permissionService;

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
