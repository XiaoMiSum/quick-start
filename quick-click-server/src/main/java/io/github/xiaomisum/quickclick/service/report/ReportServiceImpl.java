package io.github.xiaomisum.quickclick.service.report;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import io.github.xiaomisum.quickclick.controller.project.days.vo.DaysQueryReqVO;
import io.github.xiaomisum.quickclick.controller.project.days.vo.DeveloperDaysPageRespVO;
import io.github.xiaomisum.quickclick.controller.project.days.vo.TesterDaysPageRespVO;
import io.github.xiaomisum.quickclick.controller.report.vo.*;
import io.github.xiaomisum.quickclick.dal.dataobject.report.DeveloperBasicData;
import io.github.xiaomisum.quickclick.dal.dataobject.report.ProjectRangeDataBaseDO;
import io.github.xiaomisum.quickclick.dal.dataobject.report.TesterBasicData;
import io.github.xiaomisum.quickclick.dal.mapper.report.DeveloperBasicDataMapper;
import io.github.xiaomisum.quickclick.dal.mapper.report.ProjectMonthDataMapper;
import io.github.xiaomisum.quickclick.dal.mapper.report.ProjectWeekDataMapper;
import io.github.xiaomisum.quickclick.dal.mapper.report.TesterBasicDataMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class ReportServiceImpl implements ReportService {

    @Resource
    private DeveloperBasicDataMapper developerBasicDataMapper;
    @Resource
    private TesterBasicDataMapper testerBasicDataMapper;
    @Resource
    private ProjectWeekDataMapper projectWeekDataMapper;
    @Resource
    private ProjectMonthDataMapper projectMonthDataMapper;

    @Override
    public List<DeveloperBugReopenRespVO> getDevReopenedBugs(ReportQueryReqVO req) {
        return developerBasicDataMapper.selectSumList3(req.getStart(), req.getEnd());
    }

    @Override
    public PageResult<DeveloperDaysPageRespVO> getDevDays(DaysQueryReqVO req) {
        return developerBasicDataMapper.selectPage(req);
    }

    @Override
    public void updateDevDays(DeveloperBasicData data) {
        developerBasicDataMapper.updateById(data);
    }

    @Override
    public PageResult<TesterDaysPageRespVO> getTestDays(DaysQueryReqVO req) {
        return testerBasicDataMapper.selectPage(req);
    }

    @Override
    public void updateTestDays(TesterBasicData data) {
        testerBasicDataMapper.updateById(data);
    }

    @Override
    public List<QualificationRateRespVO.Item> getQualificationRateList(ReportQueryReqVO req, boolean week) {
        return week ? projectWeekDataMapper.selectQualificationRateList(req.getStart(), req.getEnd()) :
                projectMonthDataMapper.selectQualificationRateList(req.getStart(), req.getEnd());
    }

    @Override
    public QualificationRateRespVO getQualificationRate(ReportQueryReqVO req, boolean week) {
        var title = new TitleRespVO("项目开发质量合格率排名", req.getStart() + " - " + req.getEnd());
        var result = new QualificationRateRespVO().setTitle(title);
        var items = week ? projectWeekDataMapper.selectQualificationRateList(req.getStart(), req.getEnd()) :
                projectMonthDataMapper.selectQualificationRateList(req.getStart(), req.getEnd());
        if (CollectionUtil.isEmpty(items)) {
            return result.setFirst(List.of()).setItems(List.of());
        }
        items.sort(Comparator.comparing(QualificationRateRespVO.Item::getQualificationRate).reversed());
        var first = items.stream().filter(item -> item.getQualificationRate().compareTo(items.getFirst().getQualificationRate()) >= 0).toList();
        return result.setFirst(first).setItems(items);
    }

    @Override
    public ProjectTrendRespVO getProjectTrend(boolean week) {
        var title = new TitleRespVO("项目开发合格率推移图", "所有项目合计");
        var result = new ProjectTrendRespVO().setTitle(title);
        var items = week ? projectWeekDataMapper.selectQualificationRateTrendList() :
                projectMonthDataMapper.selectQualificationRateTrendList();
        items.sort(Comparator.comparing(ProjectTrendRespVO.Item::getEnd));
        return result.setItems(items);
    }

    @Override
    public DeveloperBugRespVO getDevBugRank(ReportQueryReqVO req, boolean week) {
        var title = new TitleRespVO("缺陷数责任人排名", req.getStart() + " - " + req.getEnd());
        var result = new DeveloperBugRespVO().setTitle(title);
        var items = Lists.newArrayList(developerBasicDataMapper.selectSumList2(req.getStart(), req.getEnd())
                .stream().filter(item -> Objects.nonNull(item.getTotal()) && item.getTotal() > 0).toList());
        items.sort(Comparator.comparing(DeveloperBugRespVO.Item::getTotal).reversed());
        var first = items.stream().filter(item -> item.getTotal() >= (items.getFirst().getTotal())).toList();

        return result.setFirst(first).setItems(items);
    }

    @Override
    public List<ProjectBugRespVO> getProjectBug(ReportQueryReqVO req, boolean week) {
        var items = week ? projectWeekDataMapper.selectBugList(req.getStart(), req.getEnd()) :
                projectMonthDataMapper.selectBugList(req.getStart(), req.getEnd());
        // 设置 本期缺陷总数 = 本期新增 + 上期遗留 、本期未关闭 = 本期新增 + 上期遗留 - 本期关闭
        items.forEach(item -> item.setBugTotal(item.getThisRangeBugTotal() + item.getLastRangeBugTotal())
                .setThisRangeUnclosedBugTotal(item.getThisRangeBugTotal() + item.getLastRangeBugTotal() - item.getThisRangeClosedBugTotal()));
        return items.stream().filter(item -> item.getBugTotal() <= 0).toList();
    }

    @Override
    public BugTrendRespVO getBugTrend(ReportQueryReqVO req, boolean week) {
        List<? extends ProjectRangeDataBaseDO> items = week ? projectWeekDataMapper.selectBugList3(req.getStart(), req.getEnd()) :
                projectMonthDataMapper.selectBugList3(req.getStart(), req.getEnd());
        items.sort(Comparator.comparing(ProjectRangeDataBaseDO::getStartDate));
        List<BugTrendRespVO.Item> rs = Lists.newArrayList();
        items.forEach(item -> rs.add(new BugTrendRespVO.Item().setMonth(item.getStartDate()).setTotal(item.getThisRangeBugTotal())));
        var title = new TitleRespVO("缺陷数对比", req.getStart() + " - " + req.getEnd());
        return new BugTrendRespVO().setTitle(title).setItems(rs);
    }

    @Override
    public List<BugMoneyRespVO> getBugsMoney(ReportQueryReqVO req) {
        var results = new ArrayList<BugMoneyRespVO>();
        // 1 获取开发人员
        developerBasicDataMapper.selectSumList4(req.getStart(), req.getEnd()).stream().filter(item -> item.getFixedBugDuration() > 0)
                .forEach(item -> results.add(new BugMoneyRespVO(item.getUserId(), item.getFixedBugTotal(), item.getFixedBugDuration())));
        // 2 获取测试人员
        testerBasicDataMapper.selectSumList(req.getStart(), req.getEnd()).stream().filter(item -> item.getValidatedBugDuration() > 0)
                .forEach(item -> results.add(new BugMoneyRespVO(item.getUserId(), item.getReopenedBugTotal() + item.getClosedBugTotal(), item.getValidatedBugDuration())));
        return results;
    }

    @Override
    public List<BugRateRespVO> getBugsRate(ReportQueryReqVO req) {
        var results = new ArrayList<BugRateRespVO>();
        // 获取开发人员缺陷数据
        developerBasicDataMapper.selectSumList5(req.getStart(), req.getEnd()).stream().filter(item -> item.getTestcaseTotal() > 0)
                .forEach(item -> results.add(new BugRateRespVO(item.getUserId(), item.getTestcaseTotal(), item.getNewBugTotal())));
        return results;
    }

    @Override
    public List<BugRateDetailRespVO> getBugsRateDetail(ReportQueryReqVO req, Long userId) {
        return developerBasicDataMapper.selectList(req.getStart(), req.getEnd(), userId)
                .stream().filter(item -> item.getTestcaseTotal() > 0)
                .toList();
    }
}
