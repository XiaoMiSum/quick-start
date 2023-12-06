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

package com.github.xiaomisum.mstar.controller.project;

import com.github.xiaomisum.mstar.controller.project.vo.ProjectAddReqVO;
import com.github.xiaomisum.mstar.controller.project.vo.ProjectQueryReqVO;
import com.github.xiaomisum.mstar.controller.project.vo.ProjectUpdateReqVO;
import com.github.xiaomisum.mstar.convert.project.ProjectConvert;
import com.github.xiaomisum.mstar.dal.dataobject.project.Project;
import com.github.xiaomisum.mstar.service.project.ProjectService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.common.pojo.SimpleData;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Resource
    private ProjectService service;

    @GetMapping
    public Result<?> getPage(ProjectQueryReqVO req) {
        return Result.getSuccessful(ProjectConvert.INSTANCE.convert(service.getPage(req)));
    }

    @GetMapping("/{id}")
    public Result<?> get(@PathVariable String id) {
        return Result.getSuccessful(ProjectConvert.INSTANCE.convert(service.get(id)));
    }

    @PostMapping
    public Result<?> add(@RequestBody ProjectAddReqVO data) {
        service.add(ProjectConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @PutMapping
    public Result<?> update(@RequestBody ProjectUpdateReqVO data) {
        service.update(ProjectConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable String id) {
        service.remove(id);
        return Result.getSuccessful();
    }

    @GetMapping("/simple")
    public Result<List<SimpleData<String>>> getSimple() {
        // 获得用户列表，只要开启状态的
        List<Project> list = service.getList();
        return Result.getSuccessful(ProjectConvert.INSTANCE.convert(list));
    }
}
