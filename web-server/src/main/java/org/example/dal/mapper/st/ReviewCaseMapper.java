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
        );
    }

    default List<ReviewCase> selectList(Long reviewId) {
        return selectList(new LambdaQueryWrapperX<ReviewCase>().eq(ReviewCase::getReviewId, reviewId));
    }

    default List<ReviewCase> selectList(Long reviewId, Long caseId) {
        return selectList(new LambdaQueryWrapperX<ReviewCase>()
                .eq(ReviewCase::getReviewId, reviewId)
                .eq(ReviewCase::getCaseId, caseId)
        );
    }

}
