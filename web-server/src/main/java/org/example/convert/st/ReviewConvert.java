package org.example.convert.st;

import com.google.common.collect.Lists;
import org.example.controller.st.review.vo.*;
import org.example.dal.dataobject.st.Review;
import org.example.dal.dataobject.st.ReviewCase;
import org.example.dal.dataobject.st.Testcase;
import org.example.model.dto.TestcaseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Mapper
public interface ReviewConvert {
    ReviewConvert INSTANCE = Mappers.getMapper(ReviewConvert.class);

    ReviewCase convert(TestcaseDTO testcase);

    Review convert(ReviewAddReqVO bean);

    Review convert(ReviewUpdateReqVO bean);

    ReviewRespVO convert(Review review);

    default ReviewPageRespVO convert1(Review bean) {
        return (ReviewPageRespVO) new ReviewPageRespVO()
                .setId(bean.getId())
                .setActualEndTime(bean.getActualEndTime())
                .setActualStartTime(bean.getActualStartTime())
                .setProjectId(bean.getProjectId())
                .setSpeaker(bean.getSpeaker())
                .setName(bean.getName())
                .setExpectedStartTime(bean.getExpectedStartTime())
                .setExpectedEndTime(bean.getExpectedEndTime())
                .setReviewers(bean.getReviewers());
    }

    default List<ReviewPageRespVO> convert1(List<Review> beans) {
        List<ReviewPageRespVO> result = Lists.newArrayList();
        beans.forEach(item -> result.add(convert1(item)));
        return result;
    }

    default ReviewCasePageRespVO convert2(Testcase bean) {
        return (ReviewCasePageRespVO) new ReviewCasePageRespVO()
                .setReviewed(bean.getReviewed())
                .setCaseId(bean.getId())
                .setName(bean.getName())
                .setChargeUserId(bean.getChargeUserId())
                .setTags(bean.getTags())
                .setLevel(bean.getLevel())
                .setModuleId(bean.getModuleId())
                .setProjectId(bean.getProjectId());
    }

    ReviewCaseRespVO convert(ReviewCase bean);

    default List<ReviewCasePageRespVO> convert2(List<Testcase> beans) {
        List<ReviewCasePageRespVO> result = Lists.newArrayList();
        beans.forEach(item -> result.add(convert2(item)));
        return result;
    }

    PageResult<ReviewPageRespVO> convert(PageResult<Review> beans);

    List<ReviewCase> convert(List<TestcaseDTO> testcases);

    PageResult<ReviewCasePageRespVO> convert1(PageResult<ReviewCase> page);

    PageResult<ReviewCasePageRespVO> convert2(PageResult<Testcase> page);
}
