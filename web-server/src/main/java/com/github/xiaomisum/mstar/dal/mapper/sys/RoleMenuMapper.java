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

package com.github.xiaomisum.mstar.dal.mapper.sys;

import com.github.xiaomisum.mstar.dal.dataobject.sys.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Mapper
public interface RoleMenuMapper extends BaseMapperX<RoleMenu> {

    default List<RoleMenu> selectListByRoleId(Long roleId) {
        return selectList(new LambdaQueryWrapperX<RoleMenu>().eq(RoleMenu::getRoleId, roleId));
    }

    default void insertList(Long roleId, Collection<Long> menuIds) {
        List<RoleMenu> list = menuIds.stream().map(menuId -> {
            RoleMenu entity = new RoleMenu();
            entity.setRoleId(roleId);
            entity.setMenuId(menuId);
            return entity;
        }).toList();
        list.forEach(this::insert);
    }

    default void deleteListByRoleIdAndMenuIds(Long roleId, Collection<Long> menuIds) {
        delete(new LambdaQueryWrapperX<RoleMenu>().eq(RoleMenu::getRoleId, roleId)
                .in(RoleMenu::getMenuId, menuIds));
    }

    default void deleteListByMenuId(Long menuId) {
        delete(new LambdaQueryWrapperX<RoleMenu>().eq(RoleMenu::getMenuId, menuId));
    }

    default void deleteListByRoleId(Long roleId) {
        delete(new LambdaQueryWrapperX<RoleMenu>().eq(RoleMenu::getRoleId, roleId));
    }

    @Select("SELECT id FROM sys_role_menu WHERE update_time > #{maxUpdateTime} LIMIT 1")
    Long selectExistsByUpdateTimeAfter(Date maxUpdateTime);

}
