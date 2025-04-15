package io.github.xiaomisum.quickclick.convert.report;

import io.github.xiaomisum.quickclick.controller.project.days.vo.DeveloperDaysUpdateReqVO;
import io.github.xiaomisum.quickclick.controller.project.days.vo.TesterDaysUpdateReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.report.DeveloperBasicData;
import io.github.xiaomisum.quickclick.dal.dataobject.report.TesterBasicData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReportConvert {

    ReportConvert INSTANCE = Mappers.getMapper(ReportConvert.class);

    DeveloperBasicData convert(DeveloperDaysUpdateReqVO bean);

    TesterBasicData convert(TesterDaysUpdateReqVO bean);
}
