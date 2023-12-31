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

package com.github.xiaomisum.mstar.controller.sys.post;

import com.github.xiaomisum.mstar.controller.sys.post.vo.*;
import com.github.xiaomisum.mstar.convert.sys.PostConvert;
import com.github.xiaomisum.mstar.dal.dataobject.sys.Post;
import com.github.xiaomisum.mstar.service.sys.post.PostService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.enums.CommonStatusEnum;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Resource
    private PostService postService;

    @GetMapping
    @PreAuthorize("@ss.hasPermission('system:post:query')")
    public Result<PageResult<PostRespVO>> getPage(PostQueryReqVO req) {
        PageResult<PostRespVO> result = PostConvert.INSTANCE.convert(postService.getPage(req));
        result.getList().sort(Comparator.comparing(PostRespVO::getSort));
        return Result.getSuccessful(result);
    }

    @PostMapping
    @PreAuthorize("@ss.hasPermission('system:post:add')")
    public Result<?> add(@RequestBody PostAddReqVO req) {
        postService.verify(req.getCode(), req.getName(), null);
        postService.add(PostConvert.INSTANCE.convert(req));
        return Result.getSuccessful();
    }

    @PutMapping
    @PreAuthorize("@ss.hasPermission('system:post:update')")
    public Result<?> update(@RequestBody PostUpdateReqVO req) {
        postService.verify(req.getCode(), req.getName(), req.getId());
        postService.update(PostConvert.INSTANCE.convert(req));
        return Result.getSuccessful();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:post:update')")
    public Result<?> get(@PathVariable("id") Long id) {
        return Result.getSuccessful(PostConvert.INSTANCE.convert(postService.get(id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:post:remove')")
    public Result<?> remove(@PathVariable("id") Long id) {
        postService.remove(id);
        return Result.getSuccessful();
    }

    @GetMapping("/simple")
    public Result<List<PostSimpleRespVO>> getSimplePosts() {
        // 获得岗位列表，只要开启状态的
        List<Post> list = postService.getList(null, Collections.singleton(CommonStatusEnum.ENABLE.getStatus()));
        // 排序后，返回给前端
        list.sort(Comparator.comparing(Post::getSort));
        return Result.getSuccessful(PostConvert.INSTANCE.convert(list));
    }
}
