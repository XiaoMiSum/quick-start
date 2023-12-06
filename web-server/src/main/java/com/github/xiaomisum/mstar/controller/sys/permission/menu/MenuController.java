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

package com.github.xiaomisum.mstar.controller.sys.permission.menu;

import com.github.xiaomisum.mstar.controller.sys.permission.menu.vo.*;
import com.github.xiaomisum.mstar.convert.sys.MenuConvert;
import com.github.xiaomisum.mstar.dal.dataobject.sys.Menu;
import com.github.xiaomisum.mstar.service.sys.permission.MenuService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.enums.CommonStatusEnum;
import xyz.migoo.framework.common.pojo.Result;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping
    @PreAuthorize("@ss.hasPermission('system:menu:query')")
    public Result<List<MenuRespVO>> getMenus(MenuQueryReqVO reqVO) {
        List<Menu> list = menuService.get(reqVO);
        list.sort(Comparator.comparing(Menu::getSort));
        return Result.getSuccessful(MenuConvert.INSTANCE.convert(list));
    }

    @PostMapping
    @PreAuthorize("@ss.hasPermission('system:menu:add')")
    public Result<?> createMenu(@Valid @RequestBody MenuAddReqVO reqVO) {
        menuService.add(MenuConvert.INSTANCE.convert(reqVO));
        return Result.getSuccessful();
    }

    @PutMapping
    @PreAuthorize("@ss.hasPermission('system:menu:update')")
    public Result<?> updateMenu(@Valid @RequestBody MenuUpdateReqVO reqVO) {
        menuService.update(MenuConvert.INSTANCE.convert(reqVO));
        return Result.getSuccessful();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:menu:remove')")
    public Result<?> deleteMenu(@PathVariable("id") Long id) {
        menuService.remove(id);
        return Result.getSuccessful();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:menu:update')")
    public Result<MenuRespVO> getMenu(@PathVariable("id") Long id) {
        return Result.getSuccessful(MenuConvert.INSTANCE.convert(menuService.get(id)));
    }

    @GetMapping("/simple")
    public Result<List<MenuSimpleRespVO>> getSimpleMenus() {
        // 获得菜单列表，只要开启状态的
        MenuQueryReqVO reqVO = new MenuQueryReqVO();
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        List<Menu> list = menuService.get(reqVO);
        // 排序后，返回给前端
        list.sort(Comparator.comparing(Menu::getSort));
        return Result.getSuccessful(MenuConvert.INSTANCE.convert0(list));
    }
}
