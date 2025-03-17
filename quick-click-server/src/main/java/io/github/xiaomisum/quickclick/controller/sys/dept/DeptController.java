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

package io.github.xiaomisum.quickclick.controller.sys.dept;

import com.github.xiaomisum.quickclick.controller.sys.dept.vo.*;
import io.github.xiaomisum.quickclick.controller.sys.dept.vo.*;
import io.github.xiaomisum.quickclick.convert.sys.DeptConvert;
import io.github.xiaomisum.quickclick.dal.dataobject.sys.Dept;
import io.github.xiaomisum.quickclick.service.sys.dept.DeptService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.enums.CommonStatusEnum;
import xyz.migoo.framework.common.pojo.Result;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/dept")
public class DeptController {
    @Resource
    private DeptService deptService;

    @GetMapping
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public Result<List<DeptRespVO>> getDeptPage(DeptQueryReqVO req) {
        // 获得用户分页列表
        List<Dept> list = deptService.getList(req);
        list.sort(Comparator.comparing(Dept::getSort));
        return Result.getSuccessful(DeptConvert.INSTANCE.convert(list));
    }

    @PostMapping
    @PreAuthorize("@ss.hasPermission('system:dept:add')")
    public Result<?> addDept(@RequestBody DeptAddReqVO addReq) {
        deptService.verify(null, addReq.getParentId(), addReq.getName());
        deptService.add(DeptConvert.INSTANCE.convert(addReq));
        return Result.getSuccessful();
    }

    @PutMapping
    @PreAuthorize("@ss.hasPermission('system:dept:update')")
    public Result<?> updateDept(@RequestBody DeptUpdateReqVO updateReqVO) {
        deptService.verify(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getName());
        deptService.update(DeptConvert.INSTANCE.convert(updateReqVO));
        return Result.getSuccessful();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:dept:update')")
    public Result<?> getDept(@PathVariable Long id) {
        return Result.getSuccessful(DeptConvert.INSTANCE.convert(deptService.get(id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:dept:remove')")
    public Result<?> removeDept(@PathVariable Long id) {
        deptService.remove(id);
        return Result.getSuccessful();
    }

    @GetMapping("/simple")
    public Result<List<DeptSimpleRespVO>> getDeptSimple(DeptQueryReqVO req) {
        // 获得用户分页列表
        req.setStatus(CommonStatusEnum.ENABLE.getStatus());
        List<Dept> list = deptService.getList(req);
        list.sort(Comparator.comparing(Dept::getSort));
        return Result.getSuccessful(DeptConvert.INSTANCE.convert0(list));
    }
}
