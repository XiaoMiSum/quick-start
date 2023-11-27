package org.example.dal.mapper.st;

import org.apache.ibatis.annotations.Mapper;
import org.example.controller.st.review.vo.ReviewCaseQueryReqVO;
import org.example.dal.dataobject.st.ReviewCase;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

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
}
