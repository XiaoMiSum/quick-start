package org.example.service.st;

import org.example.controller.st.review.vo.ReviewQueryReqVO;
import org.example.dal.dataobject.st.Review;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface ReviewService {

    PageResult<Review> getPage(ReviewQueryReqVO req);

    List<Review> getList(Long projectId);

    Review get(Long id);

    Long add(Review review);

    void update(Review review);

    void remove(Long id);
}
