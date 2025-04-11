package io.github.xiaomisum.quickclick.service.report;

import io.github.xiaomisum.quickclick.controller.report.vo.ReportQueryReqVO;
import io.github.xiaomisum.quickclick.controller.report.vo.days.DeveloperDaysPageRespVO;
import io.github.xiaomisum.quickclick.dal.dataobject.report.DeveloperBasicData;
import xyz.migoo.framework.common.pojo.PageResult;

public interface ReportService {


    PageResult<DeveloperDaysPageRespVO> getDevDays(ReportQueryReqVO req);

    void updateDevDays(DeveloperBasicData data);
}
