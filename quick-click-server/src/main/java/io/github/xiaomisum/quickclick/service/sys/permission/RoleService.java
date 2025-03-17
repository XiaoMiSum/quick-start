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

package io.github.xiaomisum.quickclick.service.sys.permission;

import io.github.xiaomisum.quickclick.controller.sys.permission.role.vo.RoleQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.sys.Role;
import org.springframework.lang.Nullable;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RoleService {

    /**
     * 初始化角色的本地缓存
     */
    void initLocalCache();

    /**
     * 删除角色
     *
     * @param id 角色编号
     */
    void remove(Long id);

    /**
     * 设置角色的数据权限
     *
     * @param id               角色编号
     * @param dataScope        数据范围
     * @param dataScopeDeptIds 部门编号数组
     */
    void updateRoleDataScope(Long id, Integer dataScope, Set<Long> dataScopeDeptIds);

    /**
     * 获得角色，从缓存中
     *
     * @param id 角色编号
     * @return 角色
     */
    Role getRoleFromCache(Long id);

    /**
     * 获得角色列表
     *
     * @param statuses 筛选的状态。允许空，空时不筛选
     * @return 角色列表
     */
    List<Role> getRoles(@Nullable Collection<Integer> statuses);

    /**
     * 获得角色数组，从缓存中
     *
     * @param ids 角色编号数组
     * @return 角色数组
     */
    List<Role> getRolesFromCache(Collection<Long> ids);

    /**
     * 判断角色数组中，是否有管理员
     *
     * @param roleList 角色数组
     * @return 是否有管理员
     */
    boolean hasAnyAdmin(Collection<Role> roleList);

    /**
     * 判断角色编号数组中，是否有管理员
     *
     * @param ids 角色编号数组
     * @return 是否有管理员
     */
    default boolean hasAnyAdmin(Set<Long> ids) {
        return hasAnyAdmin(getRolesFromCache(ids));
    }

    /**
     * 获得角色
     *
     * @param id 角色编号
     * @return 角色
     */
    Role get(Long id);

    List<Role> getList(Integer status);

    PageResult<Role> getPage(RoleQueryReqVO req);

    void verify(String code, String name, Long id);

    void add(Role role);

    void update(Role role);
}
