package org.example.convert.st;

import org.example.controller.st.testcase.vo.TestcaseAddReqVO;
import org.example.controller.st.testcase.vo.TestcasePageRespVO;
import org.example.controller.st.testcase.vo.TestcaseRespVO;
import org.example.controller.st.testcase.vo.TestcaseUpdateReqVO;
import org.example.dal.dataobject.st.Testcase;
import org.example.model.dto.TestcaseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Mapper
public interface TestcaseConvert {

    TestcaseConvert INSTANCE = Mappers.getMapper(TestcaseConvert.class);

    Testcase convert(TestcaseAddReqVO bean);

    Testcase convert(TestcaseUpdateReqVO bean);

    TestcaseRespVO convert1(Testcase bean);

    PageResult<TestcasePageRespVO> convert(PageResult<Testcase> beans);

    default TestcaseDTO convert(Testcase testcase) {
        return new TestcaseDTO().setCaseId(testcase.getId())
                .setName(testcase.getName())
                .setLevel(testcase.getLevel())
                .setTags(testcase.getTags())
                .setPrecondition(testcase.getPrecondition())
                .setSteps(testcase.getSteps())
                .setModuleId(testcase.getModuleId())
                .setProjectId(testcase.getProjectId())
                .setChargeUserId(testcase.getChargeUserId());
    }

    List<TestcaseDTO> convert(List<Testcase> testcases);

}