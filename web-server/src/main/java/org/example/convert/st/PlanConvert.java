package org.example.convert.st;

import org.example.dal.dataobject.st.PlanCase;
import org.example.model.dto.TestcaseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PlanConvert {

    PlanConvert INSTANCE = Mappers.getMapper(PlanConvert.class);

    PlanCase convert(TestcaseDTO testcase);

    List<PlanCase> convert(List<TestcaseDTO> testcases);

}
