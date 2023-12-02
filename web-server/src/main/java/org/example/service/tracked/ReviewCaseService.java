package org.example.service.tracked;

import org.example.controller.tracked.review.vo.ReviewCaseExecuteVO;
import org.example.controller.tracked.review.vo.ReviewCaseQueryReqVO;
import org.example.dal.dataobject.tracked.ReviewCase;
import org.example.enums.ResultEnum;
import org.example.model.dto.Statistics;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface ReviewCaseService {

    PageResult<ReviewCase> getPage(ReviewCaseQueryReqVO req);

    ReviewCase get(Long id);

    List<ReviewCase> getList(Long reviewId);

    List<ReviewCase> getList(Long reviewId, ResultEnum result);

    List<ReviewCase> getListNotInCaseIds(Long reviewId, List<Long> notInCaseIds);

    List<ReviewCase> getListGtId(String opt, Long reviewId, Long id);

    void add(List<ReviewCase> data);

    void update(ReviewCase reviewCase);

    void remove(Long id);

    void remove(List<Long> ids);

    void reviewed(ReviewCaseExecuteVO execute);

    Statistics statistics(Long reviewId);
}
