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

package io.github.xiaomisum.quickclick.controller.quality.bug;

import io.github.xiaomisum.quickclick.controller.quality.bug.vo.*;
import io.github.xiaomisum.quickclick.convert.qualitycenter.BugConvert;
import io.github.xiaomisum.quickclick.service.qualitycenter.bug.BugService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.annotation.CurrentUser;

/**
 * 缺陷跟踪
 */
@RestController
@RequestMapping("/quality-center/bug")
public class BugController {

    @Resource
    private BugService service;

    /**
     * 缺陷列表
     *
     * @param req 查询条件
     * @return 缺陷列表
     */
    @GetMapping
    public Result<?> getPage(BugQueryReqVO req) {
        // 获得测试缺陷分页列表
        PageResult<BugPageRespVO> result = BugConvert.INSTANCE.convert(service.getPage(req));
        return Result.getSuccessful(result);
    }

    /**
     * 缺陷明细
     *
     * @param id 缺陷编号
     * @return 缺陷明细
     */
    @GetMapping("/{id}")
    public Result<?> get(@PathVariable("id") String id) {
        return Result.getSuccessful(BugConvert.INSTANCE.convert(service.get(id)));
    }

    /**
     * 新增缺陷
     *
     * @param data 缺陷信息
     * @return 处理结果
     */
    @PostMapping
    public Result<?> add(@RequestBody @Valid BugAddReqVO data, @CurrentUser LoginUser user) {
        data.setCreatorId(user.getId());
        String id = service.add(BugConvert.INSTANCE.convert(data));
        return Result.getSuccessful(id);
    }

    /**
     * 更新缺陷
     *
     * @param data 缺陷信息
     * @return 处理结果
     */
    @PutMapping
    public Result<?> update(@RequestBody @Valid BugUpdateReqVO data) {
        service.update(BugConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    /**
     * 确认缺陷
     *
     * @param data 缺陷信息
     * @return 处理结果
     */
    @PutMapping("confirmed")
    public Result<?> confirm(@RequestBody @Valid BugOptionReqVO data) {
        service.confirm(data.getIds());
        return Result.getSuccessful();
    }

    /**
     * 修复缺陷
     *
     * @param data 缺陷信息
     * @return 处理结果
     */
    @PutMapping("fixed")
    public Result<?> fix(@RequestBody @Valid BugFixReqVO data) {
        service.fix(BugConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    /**
     * 激活缺陷
     *
     * @param id 缺陷编号
     * @return 处理结果
     */
    @PutMapping("/reopened/{id}")
    public Result<?> reopen(@PathVariable("id") String id) {
        service.reopen(id);
        return Result.getSuccessful();
    }

    /**
     * 关闭缺陷
     *
     * @param id 缺陷编号
     * @return 处理结果
     */
    @PutMapping("/closed/{id}")
    public Result<?> close(@PathVariable("id") String id) {
        service.close(id);
        return Result.getSuccessful();
    }

    /**
     * 删除缺陷
     *
     * @param id 编号
     * @return 处理结果
     */
    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable("id") String id) {
        service.remove(id);
        return Result.getSuccessful();
    }

}
