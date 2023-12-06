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


import com.github.xiaomisum.mstar.controller.sys.permission.menu.vo.MenuQueryReqVO;
import com.github.xiaomisum.mstar.dal.dataobject.sys.Menu;

import java.util.Collection;
import java.util.List;

public interface MenuService {

    /**
     * 初始化菜单的本地缓存
     */
    void initLocalCache();

    /**
     * 删除菜单
     *
     * @param id 菜单编号
     */
    void remove(Long id);

    /**
     * 获得所有菜单列表
     *
     * @return 菜单列表
     */
    List<Menu> get(MenuQueryReqVO reqVO);

    /**
     * 获得所有菜单列表
     *
     * @return 菜单列表
     */
    List<Menu> get();

    /**
     * 获得所有菜单，从缓存中
     * <p>
     * 任一参数为空时，则返回为空
     *
     * @param menuTypes     菜单类型数组
     * @param menusStatuses 菜单状态数组
     * @param pageTypes     页面类型数组
     * @return 菜单列表
     */
    List<Menu> listMenusFromCache(Collection<Integer> menuTypes, Collection<Integer> menusStatuses,
                                  Collection<Integer> pageTypes);

    /**
     * 获得指定编号的菜单数组，从缓存中
     * <p>
     * 任一参数为空时，则返回为空
     *
     * @param menuIds       菜单编号数组
     * @param menuTypes     菜单类型数组
     * @param menusStatuses 菜单状态数组
     * @param pageTypes     页面类型数组
     * @return 菜单数组
     */
    List<Menu> listMenusFromCache(Collection<Long> menuIds, Collection<Integer> menuTypes,
                                  Collection<Integer> menusStatuses, Collection<Integer> pageTypes);

    /**
     * 获得权限对应的菜单数组
     *
     * @param permission 权限标识
     * @return 数组
     */
    List<Menu> getMenuListByPermissionFromCache(String permission);

    /**
     * 获得菜单
     *
     * @param id 菜单编号
     * @return 菜单
     */
    Menu get(Long id);

    void add(Menu menu);

    void update(Menu menu);
}
