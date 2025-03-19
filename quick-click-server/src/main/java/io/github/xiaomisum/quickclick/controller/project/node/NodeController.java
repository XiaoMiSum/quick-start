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

package io.github.xiaomisum.quickclick.controller.project.node;

import io.github.xiaomisum.quickclick.controller.project.node.vo.NodeAddReqVO;
import io.github.xiaomisum.quickclick.controller.project.node.vo.NodeRespVO;
import io.github.xiaomisum.quickclick.controller.project.node.vo.NodeUpdateReqVO;
import io.github.xiaomisum.quickclick.convert.project.NodeConvert;
import io.github.xiaomisum.quickclick.dal.dataobject.project.ProjectNode;
import io.github.xiaomisum.quickclick.service.project.NodeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.Result;

import java.util.List;

/**
 * 项目模块
 */
@RestController
@RequestMapping("/project/node")
public class NodeController {

    @Resource
    private NodeService service;

    @GetMapping
    public Result<List<NodeRespVO>> getList(@RequestParam String projectId) {
        List<ProjectNode> results = service.getList(projectId);
        return Result.getSuccessful(NodeConvert.INSTANCE.convert(results));
    }

    /**
     * 新增模块
     *
     * @param data 模块信息
     * @return 处理结果
     */
    @PostMapping
    public Result<?> add(@RequestBody NodeAddReqVO data) {
        service.add(NodeConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    /**
     * 更新模块
     *
     * @param data 模块信息
     * @return 处理结果
     */
    @PutMapping
    public Result<?> update(@RequestBody NodeUpdateReqVO data) {
        service.update(NodeConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    /**
     * 删除模块
     *
     * @param ids 模块编号
     * @return 处理结果
     */
    @DeleteMapping
    public Result<?> remove(@RequestParam List<String> ids) {
        service.remove(ids);
        return Result.getSuccessful();
    }
}
