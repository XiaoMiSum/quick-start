package org.example.convert.tracked;

import org.example.controller.tracked.testcase.vo.*;
import org.example.dal.dataobject.tracked.Testcase;
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

    List<TestcaseExportVO> convert1(List<Testcase> testcases);

    List<Testcase> convert2(List<TestcaseExportVO> imports);

}
