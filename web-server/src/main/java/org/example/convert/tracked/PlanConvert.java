package org.example.convert.tracked;

import com.google.common.collect.Lists;
import org.example.controller.tracked.plan.vo.PlanCasePageRespVO;
import org.example.controller.tracked.plan.vo.PlanCaseRespVO;
import org.example.controller.tracked.plan.vo.PlanPageRespVO;
import org.example.controller.tracked.plan.vo.PlanRespVO;
import org.example.dal.dataobject.tracked.Plan;
import org.example.dal.dataobject.tracked.PlanCase;
import org.example.dal.dataobject.tracked.ReviewCase;
import org.example.dal.dataobject.tracked.Testcase;
import org.example.model.dto.TestcaseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Mapper
public interface PlanConvert {

    PlanConvert INSTANCE = Mappers.getMapper(PlanConvert.class);

    PlanCase convert(TestcaseDTO testcase);

    PlanCaseRespVO convert(PlanCase bean);

    PlanRespVO convert(Plan plan);

    List<PlanCase> convert(List<TestcaseDTO> testcases);

    PageResult<PlanPageRespVO> convert(PageResult<Plan> beans);

    PageResult<PlanCasePageRespVO> convert1(PageResult<PlanCase> page);

    PageResult<PlanCasePageRespVO> convert2(PageResult<Testcase> page);

    default PlanCasePageRespVO convert2(Testcase bean) {
        return (PlanCasePageRespVO) new PlanCasePageRespVO()
                .setReviewed(bean.getReviewed())
                .setCaseId(bean.getId())
                .setName(bean.getName())
                .setChargeUserId(bean.getChargeUserId())
                .setTags(bean.getTags())
                .setLevel(bean.getLevel())
                .setModuleId(bean.getModuleId())
                .setProjectId(bean.getProjectId());
    }

    default List<PlanCase> convert1(List<ReviewCase> reviewCases, Long planId) {
        List<PlanCase> results = Lists.newArrayList();
        reviewCases.forEach(item -> results.add(convert1(item, planId)));
        return results;
    }

    default PlanCase convert1(ReviewCase reviewCase, Long planId) {
        return new PlanCase()
                .setPlanId(planId)
                .setCaseId(reviewCase.getCaseId())
                .setName(reviewCase.getName())
                .setTags(reviewCase.getTags())
                .setLevel(reviewCase.getLevel())
                .setModuleId(reviewCase.getModuleId())
                .setChargeUserId(reviewCase.getChargeUserId())
                .setPrecondition(reviewCase.getPrecondition())
                .setSteps(reviewCase.getSteps())
                .setProjectId(reviewCase.getProjectId());
    }

}
