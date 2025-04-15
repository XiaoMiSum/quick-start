package io.github.xiaomisum.quickclick.service.report;

import io.github.xiaomisum.quickclick.controller.project.days.vo.DaysQueryReqVO;
import io.github.xiaomisum.quickclick.controller.project.days.vo.DeveloperDaysPageRespVO;
import io.github.xiaomisum.quickclick.controller.project.days.vo.TesterDaysPageRespVO;
import io.github.xiaomisum.quickclick.dal.dataobject.report.DeveloperBasicData;
import io.github.xiaomisum.quickclick.dal.dataobject.report.TesterBasicData;
import xyz.migoo.framework.common.pojo.PageResult;

public interface ReportService {


    PageResult<DeveloperDaysPageRespVO> getDevDays(DaysQueryReqVO req);

    void updateDevDays(DeveloperBasicData data);

    PageResult<TesterDaysPageRespVO> getTestDays(DaysQueryReqVO req);

    void updateTestDays(TesterBasicData data);
}
