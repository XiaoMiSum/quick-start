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

package com.github.xiaomisum.mstar.dal.mapper.track;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.xiaomisum.mstar.controller.track.review.vo.ReviewQueryReqVO;
import com.github.xiaomisum.mstar.dal.dataobject.track.Review;
import com.github.xiaomisum.mstar.enums.TestStatus;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

import java.util.Date;
import java.util.List;

@Mapper
public interface ReviewMapper extends BaseMapperX<Review> {

    default Review selectOne(String projectId, String reviewId) {
        return selectOne(new LambdaQueryWrapperX<Review>()
                .eq(Review::getId, reviewId)
                .eq(Review::getProjectId, projectId)
                .orderByDesc(Review::getId));
    }

    default PageResult<Review> selectPage(ReviewQueryReqVO req) {
        return selectPage(req, new LambdaQueryWrapperX<Review>()
                .eq(Review::getProjectId, req.getProjectId())
                .eqIfPresent(Review::getName, req.getName())
                .orderByDesc(Review::getId));
    }

    default List<Review> selectList(String projectId) {
        return selectList(new LambdaQueryWrapperX<Review>().eq(Review::getProjectId, projectId));
    }

    default void updateStartTime(String reviewId) {
        update(new Review().setActualStartTime(new Date()),
                new LambdaUpdateWrapper<Review>()
                        .eq(Review::getId, reviewId)
                        .isNull(Review::getActualStartTime));
    }

    default void updateStatus(String reviewId, TestStatus status) {
        update(new Review().setStatus(status),
                new LambdaUpdateWrapper<Review>()
                        .eq(Review::getId, reviewId));
    }

    default List<Review> selectByStatus(TestStatus status) {
        return selectList(Review::getStatus, status.name());
    }
}
