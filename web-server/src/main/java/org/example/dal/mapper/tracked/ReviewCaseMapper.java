package org.example.dal.mapper.tracked;

import org.apache.ibatis.annotations.Mapper;
import org.example.controller.tracked.review.vo.ReviewCaseQueryReqVO;
import org.example.dal.dataobject.tracked.ReviewCase;
import org.example.enums.ResultEnum;
import org.example.model.dto.Statistics;
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
                .eqIfPresent(ReviewCase::getModuleId, req.getModuleId())
                .likeIfPresent(ReviewCase::getName, req.getCaseName())
                .orderByAsc(ReviewCase::getCaseId)
        );
    }

    default List<ReviewCase> selectList(Long reviewId) {
        return selectList(new LambdaQueryWrapperX<ReviewCase>()
                .eq(ReviewCase::getReviewId, reviewId)
                .orderByAsc(ReviewCase::getCaseId));
    }

    default List<ReviewCase> selectList(Long reviewId, Long caseId) {
        return selectList(new LambdaQueryWrapperX<ReviewCase>()
                .eq(ReviewCase::getReviewId, reviewId)
                .eq(ReviewCase::getCaseId, caseId)
                .orderByAsc(ReviewCase::getCaseId)
        );
    }

    default List<ReviewCase> selectListByGtId(Long reviewId, Long id) {
        return selectList(new LambdaQueryWrapperX<ReviewCase>()
                .eq(ReviewCase::getReviewId, reviewId)
                .gt(ReviewCase::getId, id)
                .orderByAsc(ReviewCase::getCaseId)
        );
    }

    default List<ReviewCase> selectListByLtId(Long reviewId, Long id) {
        return selectList(new LambdaQueryWrapperX<ReviewCase>()
                .eq(ReviewCase::getReviewId, reviewId)
                .lt(ReviewCase::getId, id)
                .orderByAsc(ReviewCase::getCaseId)
        );
    }

    default ReviewCase selectOne(Long reviewId, Long id) {
        return selectOne(new LambdaQueryWrapperX<ReviewCase>()
                .eq(ReviewCase::getReviewId, reviewId)
                .eq(ReviewCase::getId, id)
        );
    }

    default Statistics statistics(Long reviewId) {
        return selectJoinOne(Statistics.class, new MPJLambdaWrapperX<ReviewCase>()
                .select("ifNull(count(id), 0) total",
                        "sum(case when review_result = 'PASSED' then 1 else 0 end) passed",
                        "sum(case when review_result = 'UNREVIEWED' then 1 else 0 end) notstarted",
                        "sum(case when review_result = 'SKIPPED' then 1 else 0 end) skipped")
                .eq("review_id", reviewId));
    }

    default List<ReviewCase> selectList(Long reviewId, ResultEnum result) {
        return selectList(new LambdaQueryWrapperX<ReviewCase>()
                .eq(ReviewCase::getReviewId, reviewId)
                .eq(ReviewCase::getReviewResult, result)
        );
    }

    default List<ReviewCase> selectListNotInCaseIds(Long reviewId, List<Long> notInCaseIds) {
        return selectList(new LambdaQueryWrapperX<ReviewCase>()
                .eq(ReviewCase::getReviewId, reviewId)
                .notInIfPresent(ReviewCase::getCaseId, notInCaseIds)
        );
    }
}
