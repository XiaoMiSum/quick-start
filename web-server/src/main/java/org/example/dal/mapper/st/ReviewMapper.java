package org.example.dal.mapper.st;

import org.apache.ibatis.annotations.Mapper;
import org.example.controller.tracked.review.vo.ReviewQueryReqVO;
import org.example.dal.dataobject.tracked.Review;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

import java.util.List;

@Mapper
public interface ReviewMapper extends BaseMapperX<Review> {


    default PageResult<Review> selectPage(ReviewQueryReqVO req) {
        return selectPage(req, new LambdaQueryWrapperX<Review>()
                .eq(Review::getProjectId, req.getProjectId())
                .eqIfPresent(Review::getName, req.getName())
                .orderByDesc(Review::getId));
    }

    default List<Review> selectList(Long projectId) {
        return selectList(new LambdaQueryWrapperX<Review>().eq(Review::getProjectId, projectId));
    }

}
