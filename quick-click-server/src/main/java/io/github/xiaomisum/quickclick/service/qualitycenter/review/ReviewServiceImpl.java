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

import cn.hutool.core.util.IdUtil;
import io.github.xiaomisum.quickclick.controller.quality.review.vo.ReviewQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Review;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.ReviewCase;
import io.github.xiaomisum.quickclick.dal.mapper.qualitycenter.ReviewCaseMapper;
import io.github.xiaomisum.quickclick.dal.mapper.qualitycenter.ReviewMapper;
import io.github.xiaomisum.quickclick.enums.TestStatus;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.github.xiaomisum.quickclick.enums.TestStatus.*;

@Service
public class ReviewServiceImpl implements ReviewService {

    public static final String CRON = "0 0/5 8-23 * * ? ";

    @Resource
    private ReviewMapper mapper;
    @Resource
    private ReviewCaseMapper reviewCaseMapper;

    @Override
    public PageResult<Review> getPage(ReviewQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public List<Review> getList(String projectId) {
        return mapper.selectList(projectId);
    }

    @Override
    public Review get(String reviewId) {
        return mapper.selectById(reviewId);
    }

    @Override
    public String add(Review review) {
        review.setId(IdUtil.getSnowflakeNextIdStr());
        mapper.insert(review);
        return review.getId();
    }

    @Override
    public void update(Review review) {
        mapper.updateById(review);
    }

    @Override
    public void remove(String id) {
        mapper.deleteById(id);
    }

    @Override
    public void setStartTime(String reviewId) {
        mapper.updateStartTime(reviewId);
    }

    @Override
    public void setEndTime(String reviewId) {
        mapper.updateById((Review) new Review().setActualEndTime(LocalDateTime.now()).setId(reviewId));
    }

    @Override
    public Long count(Long id, TestStatus... status) {
        return mapper.selectCount(id, status);
    }

    @Scheduled(cron = CRON)
    public void scheduleUpdateStatus() {
        mapper.selectByStatus(Preparing).forEach(item -> {
            List<ReviewCase> cases = reviewCaseMapper.selectList(item.getId());
            Map<TestStatus, List<ReviewCase>> group = cases.stream()
                    .collect(Collectors.groupingBy(ReviewCase::getResult));
            List<ReviewCase> preparing = Preparing.get(group);
            List<ReviewCase> passed = Passed.get(group);
            List<ReviewCase> failed = Failed.get(group);
            List<ReviewCase> blocking = Blocking.get(group);
            List<ReviewCase> skipped = Skipped.get(group);
            List<ReviewCase> processing = Processing.get(group);
            if (preparing.isEmpty() && processing.isEmpty()) {
                // 进行中和未开始的都为空，评审完成
                mapper.updateStatus(item.getId(), Finished);
            } else if (preparing.size() == cases.size()) {
                // 准备中的数量与规划的测试用例总数一致，准备中
                mapper.updateStatus(item.getId(), Preparing);
            } else if ((passed.size() + failed.size() + blocking.size() + skipped.size() + processing.size()) < cases.size()) {
                // 成功数量 + 失败数量 + 阻塞数量 + 跳过数量 + 进行中数量 之和 小于 总数，评审进行中
                mapper.updateStatus(item.getId(), Processing);
            }
        });
    }
}
