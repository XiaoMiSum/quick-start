package org.example.service.tracked;

import jakarta.annotation.Resource;
import org.example.controller.tracked.review.vo.ReviewQueryReqVO;
import org.example.dal.dataobject.tracked.Review;
import org.example.dal.mapper.tracked.ReviewMapper;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.Date;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Resource
    private ReviewMapper mapper;

    @Override
    public PageResult<Review> getPage(ReviewQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public List<Review> getList(Long projectId) {
        return mapper.selectList(projectId);
    }

    @Override
    public Review get(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public Long add(Review review) {
        mapper.insert(review);
        return review.getId();
    }

    @Override
    public void update(Review review) {
        mapper.updateById(review);
    }

    @Override
    public void remove(Long id) {
        mapper.deleteById(id);
    }

    @Override
    public void setStartTime(Long reviewId) {
        mapper.updateById(new Review().setId(reviewId).setActualStartTime(new Date()));
    }

    @Override
    public void setEndTime(Long reviewId) {
        mapper.updateById(new Review().setId(reviewId).setActualEndTime(new Date()));
    }
}