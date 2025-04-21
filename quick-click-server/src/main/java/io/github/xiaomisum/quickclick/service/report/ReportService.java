package io.github.xiaomisum.quickclick.service.report;

import io.github.xiaomisum.quickclick.controller.project.days.vo.DaysQueryReqVO;
import io.github.xiaomisum.quickclick.controller.project.days.vo.DeveloperDaysPageRespVO;
import io.github.xiaomisum.quickclick.controller.project.days.vo.TesterDaysPageRespVO;
import io.github.xiaomisum.quickclick.controller.report.vo.*;
import io.github.xiaomisum.quickclick.dal.dataobject.report.DeveloperBasicData;
import io.github.xiaomisum.quickclick.dal.dataobject.report.TesterBasicData;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface ReportService {

    List<DeveloperBugReopenRespVO> getDevReopenedBugs(ReportQueryReqVO req);

    PageResult<DeveloperDaysPageRespVO> getDevDays(DaysQueryReqVO req);

    void updateDevDays(DeveloperBasicData data);

    PageResult<TesterDaysPageRespVO> getTestDays(DaysQueryReqVO req);

    void updateTestDays(TesterBasicData data);

    List<QualificationRateRespVO.Item> getQualificationRateList(ReportQueryReqVO req, boolean week);

    QualificationRateRespVO getQualificationRate(ReportQueryReqVO req, boolean week);

    ProjectTrendRespVO getProjectTrend(boolean week);

    DeveloperBugRespVO getDevBugRank(ReportQueryReqVO req, boolean week);

    List<ProjectBugRespVO> getProjectBug(ReportQueryReqVO req, boolean week);

    BugTrendRespVO getBugTrend(ReportQueryReqVO req, boolean week);

    List<BugMoneyRespVO> getBugsMoney(ReportQueryReqVO req);

    List<BugRateRespVO> getBugsRate(ReportQueryReqVO req);

    List<BugRateDetailRespVO> getBugsRateDetail(ReportQueryReqVO req, Long userId);
}
