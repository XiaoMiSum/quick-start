package org.example.service.st;

import org.example.controller.st.review.vo.ReviewCaseExecuteVO;
import org.example.controller.st.review.vo.ReviewCaseQueryReqVO;
import org.example.dal.dataobject.st.ReviewCase;
import org.example.enums.ResultEnum;
import org.example.model.dto.Statistics;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface ReviewCaseService {

    PageResult<ReviewCase> getPage(ReviewCaseQueryReqVO req);

    ReviewCase get(Long id);

    List<ReviewCase> getList(Long reviewId);

    List<ReviewCase> getList(Long reviewId, ResultEnum result);

    List<ReviewCase> getListGtId(String opt, Long reviewId, Long id);

    void add(List<ReviewCase> data);

    void update(ReviewCase reviewCase);

    void remove(Long id);

    void remove(List<Long> ids);

    void reviewed(ReviewCaseExecuteVO execute);

    Statistics statistics(Long reviewId);
}
