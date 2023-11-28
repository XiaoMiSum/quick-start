package org.example.service.st;

import cn.hutool.core.collection.CollectionUtil;
import jakarta.annotation.Resource;
import org.example.controller.st.review.vo.ReviewCaseExecuteVO;
import org.example.controller.st.review.vo.ReviewCaseQueryReqVO;
import org.example.dal.dataobject.st.ReviewCase;
import org.example.dal.mapper.st.ReviewCaseMapper;
import org.example.enums.ResultEnum;
import org.example.model.dto.Statistics;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ReviewCaseServiceImpl implements ReviewCaseService {

    @Resource
    private ReviewCaseMapper mapper;

    @Override
    public PageResult<ReviewCase> getPage(ReviewCaseQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public ReviewCase get(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<ReviewCase> getList(Long reviewId) {
        return mapper.selectList(reviewId);
    }

    @Override
    public List<ReviewCase> getList(Long reviewId, ResultEnum result) {
        return mapper.selectList(reviewId, result);
    }

    @Override
    public List<ReviewCase> getListGtId(String opt, Long reviewId, Long id) {
        return (Objects.equals(opt, "next")) ?
                mapper.selectListByGtId(reviewId, id) : mapper.selectListByLtId(reviewId, id);
    }

    @Override
    public void add(List<ReviewCase> list) {
        // 过滤已添加的
        mapper.insertBatch(list.stream()
                .filter(item -> CollectionUtil.isEmpty(mapper.selectList(item.getReviewId(), item.getCaseId())))
                .toList());
    }

    @Override
    public void update(ReviewCase reviewCase) {
        mapper.updateById(reviewCase);
    }

    @Override
    public void remove(Long id) {
        mapper.deleteById(id);
    }

    @Override
    public void remove(List<Long> ids) {
        mapper.deleteBatchIds(ids);
    }

    @Override
    public void reviewed(ReviewCaseExecuteVO execute) {
        ReviewCase reviewCase = new ReviewCase()
                .setId(execute.getId())
                .setReviewer(execute.getReviewer())
                .setReviewTime(new Date())
                .setReviewResult(execute.getResult());
        mapper.updateById(reviewCase);
    }

    @Override
    public Statistics statistics(Long reviewId) {
        return mapper.statistics(reviewId);
    }
}
