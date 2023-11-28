package org.example.convert.st;

import org.example.controller.st.plan.vo.PlanCasePageRespVO;
import org.example.controller.st.plan.vo.PlanCaseRespVO;
import org.example.controller.st.plan.vo.PlanPageRespVO;
import org.example.controller.st.plan.vo.PlanRespVO;
import org.example.dal.dataobject.st.Plan;
import org.example.dal.dataobject.st.PlanCase;
import org.example.dal.dataobject.st.Testcase;
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
}
