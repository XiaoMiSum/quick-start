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

package io.github.xiaomisum.quickclick.convert;

import io.github.xiaomisum.quickclick.controller.login.vo.AuthLoginReqVO;
import io.github.xiaomisum.quickclick.controller.login.vo.AuthMenuRespVO;
import io.github.xiaomisum.quickclick.controller.login.vo.AuthUserInfoRespVO;
import io.github.xiaomisum.quickclick.dal.dataobject.sys.Menu;
import io.github.xiaomisum.quickclick.dal.dataobject.sys.User;
import io.github.xiaomisum.quickclick.enums.MenuIdEnum;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.LoggerFactory;
import xyz.migoo.framework.common.util.collection.CollectionUtils;
import xyz.migoo.framework.security.core.LoginUser;

import java.util.*;

import static xyz.migoo.framework.common.enums.CommonStatusEnum.ENABLE;
import static xyz.migoo.framework.security.core.LoginUser.Client.MANAGER_CLIENT;

@Mapper
public interface AuthConvert {
    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    /**
     * 转换为登录用户对象
     *
     * @param user 管理员或会员
     * @param req  登录信息
     * @return 登录用户对象
     */
    default LoginUser convert(User user, AuthLoginReqVO req) {
        return new LoginUser()
                .setId(user.getId())
                .setName(user.getName())
                .setUpdateTime(new Date())
                .setEnabled(Objects.equals(user.getStatus(), ENABLE.getStatus()))
                .setUsername(req.getUsername())
                .setPassword(user.getPassword())
                .setClient(MANAGER_CLIENT);
    }

    default AuthUserInfoRespVO convert(User user, Set<String> permissions) {
        return new AuthUserInfoRespVO()
                .setId(user.getId())
                .setName(user.getName())
                .setAvatar(user.getAvatar())
                .setPermissions(permissions);
    }

    default AuthUserInfoRespVO convert(User user, List<Menu> menus) {
        return new AuthUserInfoRespVO()
                .setId(user.getId())
                .setName(user.getName())
                .setAvatar(user.getAvatar())
                .setDefaultProject(user.getLastProject())
                .setPermissions(CollectionUtils.convertSet(menus, Menu::getPermission));
    }

    AuthMenuRespVO convert(Menu menu);

    default List<AuthMenuRespVO> convert(List<Menu> menuList) {
        // 排序，保证菜单的有序性
        menuList.sort(Comparator.comparing(Menu::getSort));
        // 构建菜单树
        // 使用 LinkedHashMap 的原因，是为了排序 。实际也可以用 Stream API ，就是太丑了。
        Map<Long, AuthMenuRespVO> treeNodeMap = new LinkedHashMap<>();
        menuList.forEach(menu -> treeNodeMap.put(menu.getId(), convert(menu)));
        // 处理父子关系
        treeNodeMap.values().stream().filter(node -> !node.getParentId().equals(MenuIdEnum.ROOT.getId())).forEach(childNode -> {
            // 获得父节点
            AuthMenuRespVO parentNode = treeNodeMap.get(childNode.getParentId());
            if (parentNode == null) {
                LoggerFactory.getLogger(getClass()).error("[buildRouterTree][resource({}) 找不到父资源({})]",
                        childNode.getId(), childNode.getParentId());
                return;
            }
            // 将自己添加到父节点中
            if (parentNode.getChildren() == null) {
                parentNode.setChildren(new ArrayList<>());
            }
            parentNode.getChildren().add(childNode);
        });
        // 获得到所有的根节点
        return CollectionUtils.filterList(treeNodeMap.values(), node -> MenuIdEnum.ROOT.getId().equals(node.getParentId()));
    }
}
