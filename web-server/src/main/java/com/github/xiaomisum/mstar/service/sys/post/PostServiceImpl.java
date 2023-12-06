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

package com.github.xiaomisum.mstar.service.sys.post;

import com.github.xiaomisum.mstar.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import com.github.xiaomisum.mstar.controller.sys.post.vo.PostQueryReqVO;
import com.github.xiaomisum.mstar.dal.dataobject.sys.Post;
import com.github.xiaomisum.mstar.dal.mapper.sys.PostMapper;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.enums.CommonStatusEnum;
import xyz.migoo.framework.common.exception.util.ServiceExceptionUtil;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class PostServiceImpl implements PostService {

    @Resource
    private PostMapper mapper;

    @Override
    public PageResult<Post> getPage(PostQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public void verify(String code, String name, Long id) {
        if (Objects.nonNull(id)) {
            // id 非空 检查 id 是否正确
            if (Objects.isNull(mapper.selectById(id))) {
                throw ServiceExceptionUtil.get(ErrorCodeConstants.POST_NOT_FOUND);
            }
            Post post = mapper.selectByCode(code);
            // code 有数据，且 id不一致
            if (Objects.nonNull(post) && !Objects.equals(post.getId(), id)) {
                throw ServiceExceptionUtil.get(ErrorCodeConstants.POST_CODE_DUPLICATE);
            }
            post = mapper.selectByName(name);
            // name 有数据，且 id不一致
            if (Objects.nonNull(post) && !Objects.equals(post.getId(), id)) {
                throw ServiceExceptionUtil.get(ErrorCodeConstants.POST_CODE_DUPLICATE);
            }
        } else {
            // id 为空
            Post post = mapper.selectByCode(code);
            if (Objects.nonNull(post)) {
                throw ServiceExceptionUtil.get(ErrorCodeConstants.POST_CODE_DUPLICATE);
            }
            post = mapper.selectByName(name);
            if (Objects.nonNull(post)) {
                throw ServiceExceptionUtil.get(ErrorCodeConstants.POST_CODE_DUPLICATE);
            }
        }
    }

    @Override
    public void add(Post post) {
        post.setStatus(CommonStatusEnum.ENABLE.getStatus());
        mapper.insert(post);
    }

    @Override
    public void update(Post post) {
        mapper.updateById(post);
    }

    @Override
    public Post get(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public void remove(Long id) {
        mapper.deleteById(id);
    }

    @Override
    public List<Post> getList(Collection<Long> ids, Collection<Integer> statuses) {
        return mapper.selectList(ids, statuses);
    }
}
