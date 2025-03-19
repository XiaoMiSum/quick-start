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

package io.github.xiaomisum.quickclick.dal.mapper.qualitycenter;

import io.github.xiaomisum.quickclick.controller.quality.review.vo.ReviewCaseQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.ReviewCase;
import io.github.xiaomisum.quickclick.enums.TestStatus;
import io.github.xiaomisum.quickclick.model.dto.Statistics;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;
import xyz.migoo.framework.mybatis.core.MPJLambdaWrapperX;

import java.util.List;

@Mapper
public interface ReviewCaseMapper extends BaseMapperX<ReviewCase> {

    default PageResult<ReviewCase> selectPage(ReviewCaseQueryReqVO req) {
        return selectPage(req, new LambdaQueryWrapperX<ReviewCase>()
                .eq(ReviewCase::getReviewId, req.getReviewId())
                .eqIfPresent(ReviewCase::getNodeId, req.getNodeId())
                .likeIfPresent(ReviewCase::getTitle, req.getTitle())
                .orderByAsc(ReviewCase::getOriginalId)
        );
    }

    default List<ReviewCase> selectList(String reviewId) {
        return selectList(new LambdaQueryWrapperX<ReviewCase>()
                .eq(ReviewCase::getReviewId, reviewId)
                .orderByAsc(ReviewCase::getOriginalId));
    }

    default List<ReviewCase> selectList(String reviewId, String originalId) {
        return selectList(new LambdaQueryWrapperX<ReviewCase>()
                .eq(ReviewCase::getReviewId, reviewId)
                .eq(ReviewCase::getOriginalId, originalId)
                .orderByAsc(ReviewCase::getOriginalId)
        );
    }

    default List<ReviewCase> selectListByGtId(String reviewId, Long id) {
        return selectList(new LambdaQueryWrapperX<ReviewCase>()
                .eq(ReviewCase::getReviewId, reviewId)
                .gt(ReviewCase::getId, id)
                .orderByAsc(ReviewCase::getOriginalId)
        );
    }

    default List<ReviewCase> selectListByLtId(String reviewId, Long id) {
        return selectList(new LambdaQueryWrapperX<ReviewCase>()
                .eq(ReviewCase::getReviewId, reviewId)
                .lt(ReviewCase::getId, id)
                .orderByAsc(ReviewCase::getOriginalId)
        );
    }

    default Statistics statistics(String reviewId) {
        return selectJoinOne(Statistics.class, new MPJLambdaWrapperX<ReviewCase>()
                .select("ifNull(count(id), 0) total",
                        "sum(case when result = 'Pass' then 1 else 0 end) passed",
                        "sum(case when result = 'UNREVIEWED' then 1 else 0 end) notstarted",
                        "sum(case when result = 'Skip' then 1 else 0 end) skipped")
                .eq("review_id", reviewId));
    }

    default List<ReviewCase> selectList(String reviewId, TestStatus result) {
        return selectList(new LambdaQueryWrapperX<ReviewCase>()
                .eq(ReviewCase::getReviewId, reviewId)
                .eq(ReviewCase::getResult, result)
        );
    }

    default ReviewCase selectOne(String reviewId) {
        return selectOne(new LambdaQueryWrapperX<ReviewCase>()
                .eq(ReviewCase::getReviewId, reviewId)
                .orderByAsc(ReviewCase::getId)
                .last("limit 1")
        );
    }

    @Delete("""
            <script>
            delete from qc_quality_test_review_case where id in
            <foreach collection="ids" item="id" index="index" open="(" close=")" separator=", ">
              #{id}
            </foreach>
            </script>
            """)
    void removeByIds(@Param("ids") List<Long> ids);

    default List<ReviewCase> selectListNotInOriginalIds(String reviewId, List<String> originalIds) {
        return selectList(new LambdaQueryWrapperX<ReviewCase>()
                .eq(ReviewCase::getReviewId, reviewId)
                .notInIfPresent(ReviewCase::getOriginalId, originalIds));
    }
}
