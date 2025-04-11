package io.github.xiaomisum.quickclick.service.report;

import io.github.xiaomisum.quickclick.controller.report.vo.ReportQueryReqVO;
import io.github.xiaomisum.quickclick.controller.report.vo.days.DeveloperDaysPageRespVO;
import io.github.xiaomisum.quickclick.controller.report.vo.days.TesterDaysPageRespVO;
import io.github.xiaomisum.quickclick.dal.dataobject.report.DeveloperBasicData;
import io.github.xiaomisum.quickclick.dal.dataobject.report.TesterBasicData;
import xyz.migoo.framework.common.pojo.PageResult;

public interface ReportService {


    PageResult<DeveloperDaysPageRespVO> getDevDays(ReportQueryReqVO req);

    void updateDevDays(DeveloperBasicData data);

    PageResult<TesterDaysPageRespVO> getTestDays(ReportQueryReqVO req);

    void updateTestDays(TesterBasicData data);
}
