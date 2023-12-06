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

package com.github.xiaomisum.mstar.service.sys.permission;

import com.github.xiaomisum.mstar.dal.dataobject.sys.Menu;
import org.springframework.lang.Nullable;
import xyz.migoo.framework.security.core.service.SecurityPermissionFrameworkService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface PermissionService extends SecurityPermissionFrameworkService {

    /**
     * 初始化权限的本地缓存
     */
    void initLocalCache();

    /**
     * 获得角色们拥有的菜单列表，从缓存中获取
     * <p>
     * 任一参数为空时，则返回为空
     *
     * @param roleIds         角色编号数组
     * @param menuTypes       菜单类型数组
     * @param menusStatuses   菜单状态数组
     * @param isManagerClient 是否管理员客户端
     * @return 菜单列表
     */
    List<Menu> getRoleMenusFromCache(Collection<Long> roleIds, Collection<Integer> menuTypes,
                                     Collection<Integer> menusStatuses, boolean isManagerClient);

    /**
     * 获得用户拥有的角色编号集合
     *
     * @param userId       用户编号
     * @param roleStatuses 角色状态集合. 允许为空，为空时不过滤
     * @return 角色编号集合
     */
    Set<Long> getUserRoleIds(Long userId, @Nullable Collection<Integer> roleStatuses);

    /**
     * 获得角色拥有的菜单编号集合
     *
     * @param roleId 角色编号
     * @return 菜单编号集合
     */
    Set<Long> getRoleMenuIds(Long roleId);

    /**
     * 设置角色菜单
     *
     * @param roleId  角色编号
     * @param menuIds 菜单编号集合
     */
    void assignRoleMenu(Long roleId, Set<Long> menuIds);

    /**
     * 获得用户拥有的角色编号集合
     *
     * @param userId 用户编号
     * @return 角色编号集合
     */
    Set<Long> getUserRoleIs(Long userId);

    /**
     * 设置用户角色
     *
     * @param userId  角色编号
     * @param roleIds 角色编号集合
     */
    void assignUserRole(Long userId, Set<Long> roleIds);

    /**
     * 处理角色删除时，删除关联授权数据
     *
     * @param roleId 角色编号
     */
    void processRoleDeleted(Long roleId);

    /**
     * 处理菜单删除时，删除关联授权数据
     *
     * @param menuId 菜单编号
     */
    void processMenuDeleted(Long menuId);

    /**
     * 处理用户删除是，删除关联授权数据
     *
     * @param userId 用户编号
     */
    void processUserDeleted(Long userId);

}
