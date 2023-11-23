package org.example.service.st;

import org.example.controller.st.review.vo.ReviewCaseQueryReqVO;
import org.example.dal.dataobject.st.ReviewCase;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface ReviewCaseService {

    PageResult<ReviewCase> getPage(ReviewCaseQueryReqVO req);

    List<ReviewCase> getList(Long reviewId);

    List<ReviewCase> getListGtId(Long id);

    void add(List<ReviewCase> data);

    void update(ReviewCase reviewCase);

    void remove(Long id);

    void remove(List<Long> ids);
}
