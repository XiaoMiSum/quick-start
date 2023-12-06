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

package com.github.xiaomisum.mstar.controller.track.testcase;

import com.github.xiaomisum.mstar.controller.track.testcase.vo.node.NodeAddReqVO;
import com.github.xiaomisum.mstar.controller.track.testcase.vo.node.NodeRespVO;
import com.github.xiaomisum.mstar.controller.track.testcase.vo.node.NodeUpdateReqVO;
import com.github.xiaomisum.mstar.convert.track.TestcaseNodeConvert;
import com.github.xiaomisum.mstar.dal.dataobject.track.TestcaseNode;
import com.github.xiaomisum.mstar.model.dto.SimpleResp;
import com.github.xiaomisum.mstar.service.track.testcase.NodeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.Result;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/track/testcase/node")
public class NodeController {

    @Resource
    private NodeService service;

    @GetMapping
    public Result<List<NodeRespVO>> getPage(@RequestHeader("x-project-id") String projectId) {
        // 获得用户分页列表
        List<TestcaseNode> list = service.getList(projectId);
        list.sort(Comparator.comparing(TestcaseNode::getSort));
        return Result.getSuccessful(TestcaseNodeConvert.INSTANCE.convert(list));
    }

    @GetMapping("/{id}")
    public Result<?> get(@PathVariable String id) {
        return Result.getSuccessful(TestcaseNodeConvert.INSTANCE.convert(service.get(id)));
    }

    @PostMapping
    public Result<?> add(@RequestHeader("x-project-id") String projectId, @RequestBody NodeAddReqVO data) {
        data.setProjectId(projectId);
        service.add(TestcaseNodeConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @PutMapping
    public Result<?> update(@RequestHeader("x-project-id") String projectId, @RequestBody NodeUpdateReqVO data) {
        data.setProjectId(projectId);
        service.update(TestcaseNodeConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable String id) {
        service.remove(id);
        return Result.getSuccessful();
    }

    @GetMapping("/simple")
    public Result<List<SimpleResp>> getSimple(@RequestHeader("x-project-id") String projectId) {
        // 获得用户列表，只要开启状态的
        List<TestcaseNode> list = service.getList(projectId);
        return Result.getSuccessful(TestcaseNodeConvert.INSTANCE.convert1(list));
    }
}
