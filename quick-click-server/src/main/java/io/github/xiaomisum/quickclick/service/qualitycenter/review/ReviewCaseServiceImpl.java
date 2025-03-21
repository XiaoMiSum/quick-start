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

package io.github.xiaomisum.quickclick.service.qualitycenter.review;

import cn.hutool.core.collection.CollectionUtil;
import io.github.xiaomisum.quickclick.controller.quality.review.vo.ReviewCaseExecuteVO;
import io.github.xiaomisum.quickclick.controller.quality.review.vo.ReviewCaseQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.ReviewCase;
import io.github.xiaomisum.quickclick.dal.mapper.qualitycenter.ReviewCaseMapper;
import io.github.xiaomisum.quickclick.enums.TestStatus;
import io.github.xiaomisum.quickclick.model.dto.Statistics;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ReviewCaseServiceImpl implements ReviewCaseService {

    @Resource
    private ReviewCaseMapper mapper;

    @Override
    public PageResult<ReviewCase> getPage(ReviewCaseQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public ReviewCase get(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<ReviewCase> getList(String reviewId) {
        return mapper.selectList(reviewId);
    }

    @Override
    public ReviewCase getFirst(String reviewId) {
        return mapper.selectOne(reviewId);
    }

    @Override
    public List<ReviewCase> getList(String reviewId, TestStatus result) {
        return mapper.selectList(reviewId, result);
    }

    @Override
    public List<ReviewCase> getListGtId(String opt, String reviewId, Long id) {
        return (Objects.equals(opt, "next")) ?
                mapper.selectListByGtId(reviewId, id) : mapper.selectListByLtId(reviewId, id);
    }

    @Override
    public void add(List<ReviewCase> list) {
        // 过滤已添加的
        mapper.insertBatch(list.stream()
                .filter(item -> CollectionUtil.isEmpty(mapper.selectList(item.getReviewId(), item.getOriginalId())))
                .toList());
    }

    @Override
    public void update(ReviewCase reviewCase) {
        mapper.updateById(reviewCase);
    }

    @Override
    public void remove(List<Long> ids) {
        mapper.removeByIds(ids);
    }

    @Override
    public void reviewed(ReviewCaseExecuteVO execute) {
        ReviewCase reviewCase = (ReviewCase) new ReviewCase()
                .setReviewer(execute.getReviewer())
                .setReviewTime(LocalDateTime.now())
                .setResult(execute.getResult())
                .setId(execute.getId());
        mapper.updateById(reviewCase);
    }

    @Override
    public Statistics statistics(String reviewId) {
        return mapper.statistics(reviewId);
    }

    @Override
    public List<ReviewCase> getListNotInOriginalIds(String reviewId, List<String> caseIds) {
        return mapper.selectListNotInOriginalIds(reviewId, caseIds);
    }
}
