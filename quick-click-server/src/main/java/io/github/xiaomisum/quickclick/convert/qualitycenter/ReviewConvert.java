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

package io.github.xiaomisum.quickclick.convert.qualitycenter;

import com.google.common.collect.Lists;
import io.github.xiaomisum.quickclick.controller.quality.review.vo.*;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Review;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.ReviewCase;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Testcase;
import io.github.xiaomisum.quickclick.model.dto.TestcaseDTO;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.SimpleData;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ReviewConvert {
    ReviewConvert INSTANCE = Mappers.getMapper(ReviewConvert.class);

    ReviewCase convert(TestcaseDTO testcase);

    Review convert(ReviewAddReqVO bean);

    Review convert(ReviewUpdateReqVO bean);

    ReviewRespVO convert(Review review);

    ReviewPageRespVO convert1(Review bean);

    default List<ReviewPageRespVO> convert1(List<Review> beans) {
        List<ReviewPageRespVO> result = Lists.newArrayList();
        beans.forEach(item -> result.add(convert1(item)));
        return result;
    }

    ReviewCaseRespVO convert(ReviewCase bean);

    PageResult<ReviewPageRespVO> convert(PageResult<Review> beans);

    List<ReviewCase> convert(List<TestcaseDTO> testcases);

    PageResult<ReviewCasePageRespVO> convert1(PageResult<ReviewCase> page);

    PageResult<ReviewCasePageRespVO> convert2(PageResult<Testcase> page);

    default List<SimpleData> convert3(List<Review> list) {
        List<SimpleData> result = Lists.newArrayList();
        list.forEach(item -> result.add(new SimpleData(item.getId(), item.getTitle(), false)));
        return result;
    }

    default Testcase convert(@Valid ReviewCaseExecuteVO execute) {
        return (Testcase) new Testcase()
                .setLastReviewResult(execute.getResult())
                .setLastReviewTime(LocalDateTime.now())
                .setId(execute.getOriginalId());
    }
}
