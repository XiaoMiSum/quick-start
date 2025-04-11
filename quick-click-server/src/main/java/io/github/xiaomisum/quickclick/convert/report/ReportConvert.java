package io.github.xiaomisum.quickclick.convert.report;

import io.github.xiaomisum.quickclick.controller.report.vo.days.DeveloperDaysUpdateReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.report.ReportBasicData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReportConvert {

    ReportConvert INSTANCE = Mappers.getMapper(ReportConvert.class);

    ReportBasicData convert(DeveloperDaysUpdateReqVO bean);
}
