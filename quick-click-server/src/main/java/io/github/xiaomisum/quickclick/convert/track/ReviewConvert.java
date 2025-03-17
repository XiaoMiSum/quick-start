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

package io.github.xiaomisum.quickclick.convert.track;

import com.github.xiaomisum.quickclick.controller.track.review.vo.*;
import io.github.xiaomisum.quickclick.controller.track.review.vo.*;
import io.github.xiaomisum.quickclick.dal.dataobject.track.Review;
import io.github.xiaomisum.quickclick.dal.dataobject.track.ReviewCase;
import io.github.xiaomisum.quickclick.dal.dataobject.track.Testcase;
import io.github.xiaomisum.quickclick.model.dto.TestcaseDTO;
import com.google.common.collect.Lists;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.SimpleData;

import java.util.List;

@Mapper
public interface ReviewConvert {
    ReviewConvert INSTANCE = Mappers.getMapper(ReviewConvert.class);

    ReviewCase convert(TestcaseDTO testcase);

    Review convert(ReviewAddReqVO bean);

    Review convert(ReviewUpdateReqVO bean);

    ReviewRespVO convert(Review review);

    default ReviewPageRespVO convert1(Review bean) {
        return (ReviewPageRespVO) new ReviewPageRespVO()
                .setId(bean.getId())
                .setActualEndTime(bean.getActualEndTime())
                .setActualStartTime(bean.getActualStartTime())
                .setProjectId(bean.getProjectId())
                .setSpeaker(bean.getSpeaker())
                .setName(bean.getName())
                .setExpectedStartTime(bean.getExpectedStartTime())
                .setExpectedEndTime(bean.getExpectedEndTime())
                .setReviewers(bean.getReviewers());
    }

    default List<ReviewPageRespVO> convert1(List<Review> beans) {
        List<ReviewPageRespVO> result = Lists.newArrayList();
        beans.forEach(item -> result.add(convert1(item)));
        return result;
    }

    default ReviewCasePageRespVO convert2(Testcase bean) {
        return (ReviewCasePageRespVO) new ReviewCasePageRespVO()
                .setReviewed(bean.getReviewed())
                .setCaseId(bean.getId())
                .setName(bean.getName())
                .setMaintainer(bean.getMaintainer())
                .setTags(bean.getTags())
                .setLevel(bean.getLevel())
                .setNodeId(bean.getNodeId())
                .setProjectId(bean.getProjectId());
    }

    ReviewCaseRespVO convert(ReviewCase bean);

    default List<ReviewCasePageRespVO> convert2(List<Testcase> beans) {
        List<ReviewCasePageRespVO> result = Lists.newArrayList();
        beans.forEach(item -> result.add(convert2(item)));
        return result;
    }

    PageResult<ReviewPageRespVO> convert(PageResult<Review> beans);

    List<ReviewCase> convert(List<TestcaseDTO> testcases);

    PageResult<ReviewCasePageRespVO> convert1(PageResult<ReviewCase> page);

    PageResult<ReviewCasePageRespVO> convert2(PageResult<Testcase> page);

    default List<SimpleData<String>> convert3(List<Review> list) {
        List<SimpleData<String>> result = Lists.newArrayList();
        list.forEach(item -> result.add(new SimpleData<>(item.getId(), item.getName(), false)));
        return result;
    }
}
