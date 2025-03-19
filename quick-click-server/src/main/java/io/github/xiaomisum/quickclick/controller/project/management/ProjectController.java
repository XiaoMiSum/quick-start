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

package io.github.xiaomisum.quickclick.controller.project.management;

import io.github.xiaomisum.quickclick.controller.project.management.vo.ProjectAddReqVO;
import io.github.xiaomisum.quickclick.controller.project.management.vo.ProjectQueryReqVO;
import io.github.xiaomisum.quickclick.controller.project.management.vo.ProjectUpdateReqVO;
import io.github.xiaomisum.quickclick.convert.project.ProjectConvert;
import io.github.xiaomisum.quickclick.service.project.ProjectService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.Result;

import java.util.List;

/**
 * 项目管理
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Resource
    private ProjectService service;

    /**
     * 项目列表
     *
     * @param req 查询条件
     * @return 项目列表
     */
    @GetMapping
    public Result<?> getPage(ProjectQueryReqVO req) {
        return Result.getSuccessful(ProjectConvert.INSTANCE.convert(service.getPage(req)));
    }

    /**
     * 项目信息
     *
     * @param id 项目编号
     * @return 项目信息
     */
    @GetMapping("/{id}")
    public Result<?> get(@PathVariable String id) {
        return Result.getSuccessful(ProjectConvert.INSTANCE.convert(service.get(id)));
    }

    /**
     * 新增项目
     *
     * @param data 项目信息
     * @return 处理结果
     */
    @PostMapping
    public Result<?> add(@RequestBody ProjectAddReqVO data) {
        service.add(ProjectConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    /**
     * 更新项目
     *
     * @param data 项目信息
     * @return 处理结果
     */
    @PutMapping
    public Result<?> update(@RequestBody ProjectUpdateReqVO data) {
        service.update(ProjectConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    /**
     * 删除项目
     *
     * @param ids 待删除的项目
     * @return 处理结果
     */
    @DeleteMapping
    public Result<?> remove(@RequestParam List<String> ids) {
        service.remove(ids);
        return Result.getSuccessful();
    }
}
