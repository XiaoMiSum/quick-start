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

package io.github.xiaomisum.quickclick.controller.project.member;

import io.github.xiaomisum.quickclick.controller.project.member.vo.MemberAddReqVO;
import io.github.xiaomisum.quickclick.controller.project.member.vo.MemberPageReqVO;
import io.github.xiaomisum.quickclick.controller.project.member.vo.MemberPageRespVO;
import io.github.xiaomisum.quickclick.controller.project.member.vo.MemberUpdateReqVO;
import io.github.xiaomisum.quickclick.convert.project.MemberConvert;
import io.github.xiaomisum.quickclick.service.project.ProjectMemberService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.common.pojo.SimpleData;

import java.util.List;

/**
 * 项目成员
 */
@RestController
@RequestMapping("/project/member")
public class MemberController {

    @Resource
    private ProjectMemberService service;

    /**
     * 成员列表
     */
    @GetMapping
    public Result<PageResult<MemberPageRespVO>> getPage(MemberPageReqVO req) {
        return Result.getSuccessful(service.getPage(req));
    }

    /**
     * 新增项目成员
     *
     * @param data 项目成员信息
     * @return 处理结果
     */
    @PostMapping
    public Result<?> add(@RequestBody MemberAddReqVO data) {
        service.add(MemberConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    /**
     * 更新项目成员
     *
     * @param data 项目成员信息
     * @return 处理结果
     */
    @PutMapping
    public Result<?> add(@RequestBody MemberUpdateReqVO data) {
        service.update(MemberConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    /**
     * 删除项目成员
     *
     * @param ids 待删除的数据编号
     * @return 处理结果
     */
    @DeleteMapping
    public Result<?> remove(@RequestParam("ids") List<String> ids) {
        service.remove(ids);
        return Result.getSuccessful();
    }

    /**
     * 获取项目成员下拉
     *
     * @param projectId 项目编号
     * @return 项目成员下拉
     */
    @GetMapping("simple")
    public Result<List<SimpleData>> getSimpleList(@RequestParam String projectId) {
        List<MemberPageRespVO> members = service.getList(projectId);
        return Result.getSuccessful(MemberConvert.INSTANCE.convert(members));
    }

}
