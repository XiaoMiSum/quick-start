package org.example.service.st;

import jakarta.annotation.Resource;
import org.example.controller.st.review.vo.ReviewQueryReqVO;
import org.example.dal.dataobject.st.Review;
import org.example.dal.mapper.st.ReviewMapper;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

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
}
