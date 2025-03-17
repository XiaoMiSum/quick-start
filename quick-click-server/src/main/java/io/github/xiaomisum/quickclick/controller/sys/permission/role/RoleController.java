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

package io.github.xiaomisum.quickclick.controller.sys.permission.role;

import com.github.xiaomisum.quickclick.controller.sys.permission.role.vo.*;
import io.github.xiaomisum.quickclick.controller.sys.permission.role.vo.*;
import io.github.xiaomisum.quickclick.convert.sys.RoleConvert;
import io.github.xiaomisum.quickclick.dal.dataobject.sys.Role;
import io.github.xiaomisum.quickclick.service.sys.permission.PermissionService;
import io.github.xiaomisum.quickclick.service.sys.permission.RoleService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.enums.CommonStatusEnum;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static xyz.migoo.framework.common.enums.NumberConstants.N_1;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    public RoleController() {
    }

    @GetMapping
    @PreAuthorize("@ss.hasPermission('system:role:query')")
    public Result<PageResult<RoleRespVO>> getRolePage(RoleQueryReqVO req) {
        PageResult<RoleRespVO> result = RoleConvert.INSTANCE.convert(roleService.getPage(req));
        result.getList().sort(Comparator.comparing(RoleRespVO::getSort));
        return Result.getSuccessful(result);
    }

    @PostMapping
    @PreAuthorize("@ss.hasPermission('system:role:add')")
    public Result<?> addRole(@RequestBody RoleAddReqVO reqVO) {
        roleService.verify(reqVO.getCode(), reqVO.getName(), null);
        roleService.add(RoleConvert.INSTANCE.convert(reqVO));
        return Result.getSuccessful();
    }

    @PutMapping
    @PreAuthorize("@ss.hasPermission('system:role:update')")
    public Result<?> updateRole(@RequestBody RoleUpdateReqVO reqVO) {
        roleService.verify(reqVO.getCode(), reqVO.getName(), reqVO.getId());
        roleService.update(RoleConvert.INSTANCE.convert(reqVO));
        return Result.getSuccessful();
    }

    @PutMapping("/status")
    @PreAuthorize("@ss.hasPermission('system:role:update')")
    public Result<?> updateRole(@RequestBody RoleStatusUpdateReqVO reqVO) {
        roleService.update(RoleConvert.INSTANCE.convert(reqVO));
        return Result.getSuccessful();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:role:update')")
    public Result<?> getRole(@PathVariable Long id) {
        return Result.getSuccessful(RoleConvert.INSTANCE.convert(roleService.get(id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:role:remove')")
    public Result<?> removeRole(@PathVariable Long id) {
        roleService.remove(id);
        return Result.getSuccessful();
    }

    @GetMapping("/simple")
    public Result<List<RoleSimpleRespVO>> getSimpleMenus() {
        // 获得角色列表，只要开启状态 且 过滤 超级管理员
        List<Role> list = roleService.getList(CommonStatusEnum.ENABLE.getStatus())
                .stream().filter(role -> role.getId() > N_1).toList();
        return Result.getSuccessful(RoleConvert.INSTANCE.convert(list));
    }

    @GetMapping("/{roleId}/menu")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-menu')")
    public Result<Set<Long>> getRoleMenus(@PathVariable("roleId") Long roleId) {
        return Result.getSuccessful(permissionService.getRoleMenuIds(roleId));
    }

    @PostMapping("/{roleId}/menu")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-menu')")
    public Result<?> assignRoleMenus(@PathVariable("roleId") Long roleId, @Valid @RequestBody PermissionAssignRoleMenuReqVO reqVO) {
        reqVO.setRoleId(roleId);
        permissionService.assignRoleMenu(reqVO.getRoleId(), reqVO.getMenuIds());
        return Result.getSuccessful(true);
    }
}
