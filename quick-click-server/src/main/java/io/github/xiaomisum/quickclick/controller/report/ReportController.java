package io.github.xiaomisum.quickclick.controller.report;

import io.github.xiaomisum.quickclick.controller.report.vo.*;
import io.github.xiaomisum.quickclick.service.report.ReportService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.migoo.framework.common.pojo.Result;

import java.util.List;

@RestController
@RequestMapping("/charts")
public class ReportController {

    @Resource
    private ReportService reportService;

    /**
     * 项目合格率 周排名
     *
     * @param req
     * @return
     */
    @GetMapping("/project/rank/week")
    public Result<?> getProjectRankWeek(ReportQueryReqVO req) {
        return Result.getSuccessful(reportService.getQualificationRate(req, true));
    }

    /**
     * 项目合格率 月排名
     *
     * @param req
     * @return
     */
    @GetMapping("/project/rank/month")
    public Result<QualificationRateRespVO> getProjectRankMonth(ReportQueryReqVO req) {
        return Result.getSuccessful(reportService.getQualificationRate(req, false));
    }

    /**
     * 项目合格率 周推移
     *
     * @return
     */
    @GetMapping("/project/trend/week")
    public Result<ProjectTrendRespVO> getProjectTrendWeek() {
        return Result.getSuccessful(reportService.getProjectTrend(true));
    }

    /**
     * 项目合格率 月推移
     *
     * @return
     */
    @GetMapping("/project/trend/month")
    public Result<ProjectTrendRespVO> getProjectTrendMonth() {
        return Result.getSuccessful(reportService.getProjectTrend(false));
    }

    /**
     * 缺陷总数 月推移
     *
     * @param req
     * @return
     */
    @GetMapping("/bug/trend/month")
    public Result<BugTrendRespVO> getBugTrendMonth(ReportQueryReqVO req) {
        return Result.getSuccessful(reportService.getBugTrend(req, false));
    }

    /**
     * 缺陷跟踪解决 周
     *
     * @param req
     * @return
     */
    @GetMapping("/project/bug/week")
    public Result<List<ProjectBugRespVO>> getProjectBugWeek(ReportQueryReqVO req) {
        return Result.getSuccessful(reportService.getProjectBug(req, true));
    }

    /**
     * 缺陷跟踪解决 月
     *
     * @param req
     * @return
     */
    @GetMapping("/project/bug/month")
    public Result<List<ProjectBugRespVO>> getProjectBugMonth(ReportQueryReqVO req) {
        return Result.getSuccessful(reportService.getProjectBug(req, false));
    }

    /**
     * 开发人员缺陷数排名 周
     *
     * @param req
     * @return
     */
    @GetMapping("/developer/rank/week")
    public Result<DeveloperBugRespVO> getDevBugRankWeek(ReportQueryReqVO req) {
        return Result.getSuccessful(reportService.getDevBugRank(req, true));
    }

    /**
     * 开发人员缺陷数排名 月
     *
     * @param req
     * @return
     */
    @GetMapping("/developer/rank/month")
    public Result<DeveloperBugRespVO> getDevBugRankMonth(ReportQueryReqVO req) {
        return Result.getSuccessful(reportService.getDevBugRank(req, false));
    }

    /**
     * 开发人员激活次数
     *
     * @param req
     * @return
     */
    @GetMapping("/developer/reopened")
    public Result<List<DeveloperBugReopenRespVO>> getDevBugReopenedWeek(ReportQueryReqVO req) {
        return Result.getSuccessful(reportService.getDevReopenedBugs(req));
    }

    /**
     * 缺陷修复成本
     *
     * @param req
     * @return
     */
    @GetMapping("/bug/money")
    public Result<List<BugMoneyRespVO>> getBugsMoney(ReportQueryReqVO req) {
        return Result.getSuccessful(reportService.getBugsMoney(req));
    }

    /**
     * 开发人员缺陷率
     *
     * @param req
     * @return
     */
    @GetMapping("/bug/rate")
    public Result<List<BugRateRespVO>> getBugsRate(ReportQueryReqVO req) {
        return Result.getSuccessful(reportService.getBugsRate(req));
    }

    /**
     * 开发人员缺陷率 明细
     *
     * @param req
     * @return
     */
    @GetMapping("/bug/rate/detail")
    public Result<List<BugRateDetailRespVO>> getBugsRate(ReportQueryReqVO req, @RequestParam("userId") Long userId) {
        return Result.getSuccessful(reportService.getBugsRateDetail(req, userId));
    }
}
