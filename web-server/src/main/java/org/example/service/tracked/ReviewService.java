package org.example.service.tracked;

import org.example.controller.tracked.review.vo.ReviewQueryReqVO;
import org.example.dal.dataobject.tracked.Review;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface ReviewService {

    PageResult<Review> getPage(ReviewQueryReqVO req);

    List<Review> getList(Long projectId);

    Review get(Long id);

    Long add(Review review);

    void update(Review review);

    void remove(Long id);

    void setStartTime(Long reviewId);

    void setEndTime(Long reviewId);
}
