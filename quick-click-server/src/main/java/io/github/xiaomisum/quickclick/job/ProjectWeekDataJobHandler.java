package io.github.xiaomisum.quickclick.job;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import io.github.xiaomisum.quickclick.dal.dataobject.project.ProjectMember;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Bug;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.BugUnclosedRecord;
import io.github.xiaomisum.quickclick.dal.dataobject.report.DeveloperBasicData;
import io.github.xiaomisum.quickclick.dal.dataobject.report.ProjectWeekData;
import io.github.xiaomisum.quickclick.dal.mapper.qualitycenter.BugUncloseRecordMapper;
import io.github.xiaomisum.quickclick.dal.mapper.report.DeveloperBasicDataMapper;
import io.github.xiaomisum.quickclick.dal.mapper.report.ProjectWeekDataMapper;
import io.github.xiaomisum.quickclick.job.param.JobParam;
import io.github.xiaomisum.quickclick.service.project.ProjectMemberService;
import io.github.xiaomisum.quickclick.service.qualitycenter.bug.BugService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.migoo.framework.common.util.json.JsonUtils;
import xyz.migoo.framework.quartz.core.handler.JobHandler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProjectWeekDataJobHandler implements JobHandler {
    @Resource
    private ProjectMemberService projectMemberService;
    @Resource
    private BugService bugService;
    @Resource
    private DeveloperBasicDataMapper developerBasicDataMapper;
    @Resource
    private ProjectWeekDataMapper projectWeekDataMapper;
    @Resource
    private BugUncloseRecordMapper uncloseRecordMapper;

    @Override
    public String execute(String param, Long jobLogId) throws Exception {
        // 1、 通过配置的 postId 获取项目成员列表数据
        var jobParam = JsonUtils.parseObject(param, JobParam.class);
        var managerGrouping = projectMemberService.loadByPost(jobParam.getPostId())
                .stream()
                .collect(Collectors.groupingBy(ProjectMember::getProjectId));

        // 计算日期区间
        var currentDate = LocalDate.now();
        var currentDayOfWeek = currentDate.getDayOfWeek();
        var daysToSubtract = currentDayOfWeek.getValue() + 6;
        var lastWeekFirstDay = currentDate.minusDays(daysToSubtract);
        var lastWeekLastDay = lastWeekFirstDay.plusDays(6);
        // 上周执行数据
        var originalData1 = developerBasicDataMapper.selectSumList(lastWeekFirstDay, lastWeekLastDay);
        // 上上周统计数据
        var lastRangeDataList = projectWeekDataMapper.selectList(lastWeekFirstDay.plusDays(-7), lastWeekLastDay.plusDays(-7));
        var lastRangeDataGrouping = lastRangeDataList.stream().collect(Collectors.groupingBy(ProjectWeekData::getProjectId));
        var unclosedBugs = uncloseRecordMapper.selectList().stream().collect(Collectors.groupingBy(BugUnclosedRecord::getProjectId));

        var results = new ArrayList<ProjectWeekData>();
        for (DeveloperBasicData item : originalData1) {
            var d = managerGrouping.get(item.getProjectId());
            var data = (ProjectWeekData) new ProjectWeekData()
                    .setProjectId(item.getProjectId())
                    .setStartDate(lastWeekFirstDay)
                    .setEndDate(lastWeekLastDay)
                    .setManagerId(CollectionUtil.isEmpty(d) ? 0L : d.getFirst().getUserId())
                    .setThisRangeTestcaseTotal(item.getTestcaseTotal())
                    .setQualificationRate(item.getTestcaseTotal() <= 0 ? new BigDecimal("0.00") :
                            NumberUtil.div(item.getNewBugTotal(), item.getTestcaseTotal(), 4).multiply(new BigDecimal("100")))
                    .setLastRangeBugTotal(calcLastRangeUnClosedBugTotal(lastRangeDataGrouping.get(item.getProjectId())))
                    .setThisRangeBugTotal(item.getNewBugTotal())
                    .setThisRangeClosedBugTotal(item.getClosedBugTotal())
                    .setUnfinishedBugHandler(unfinishedBugHandlers(unclosedBugs.get(item.getProjectId())));
            if (data.getThisRangeTestcaseTotal() <= 0 && data.getThisRangeBugTotal() <= 0 &&
                    data.getThisRangeClosedBugTotal() <= 0 && data.getLastRangeBugTotal() <= 0) {
                // 本期执行用例  本期新增缺陷  本期关闭缺陷  上期遗留缺陷 均小于等于0时 过滤这条数据
                continue;
            }
            data.setCompletionDate((data.getThisRangeBugTotal() + data.getLastRangeBugTotal()) <= 0 ? null :
                    lastWeekLastDay.plusDays(3));
            results.add(data);
        }
        projectWeekDataMapper.insertBatch(results);
        return "success";
    }

    private Integer calcLastRangeUnClosedBugTotal(List<ProjectWeekData> lastRangeData) {
        if (CollectionUtil.isEmpty(lastRangeData)) {
            return 0;
        }
        var data = lastRangeData.getFirst();
        // 本期新增 + 上期遗留 - 本期关闭 = 本期遗留
        return data.getThisRangeBugTotal() + data.getLastRangeBugTotal() - data.getThisRangeClosedBugTotal();
    }

    private List<Long> unfinishedBugHandlers(List<BugUnclosedRecord> records) {
        var results = new ArrayList<Long>();
        if (CollectionUtil.isNotEmpty(records)) {
            var supervisors = records.stream().filter(item -> Objects.isNull(item.getFixer()))
                    .map(BugUnclosedRecord::getSupervisor).toList();
            var fixers = records.stream().map(BugUnclosedRecord::getFixer).filter(Objects::nonNull).toList();
            results.addAll(supervisors);
            results.addAll(fixers);
        }
        return results;
    }

    private List<Long> reopenBugHandlers(List<Bug> records) {
        var results = new ArrayList<Long>();
        if (CollectionUtil.isNotEmpty(records)) {
            var fixers = records.stream().map(Bug::getFixer).filter(Objects::nonNull).toList();
            // 开发拒绝的激活Bug分组
            var rejecters = records.stream().filter(item -> Objects.isNull(item.getFixer()))
                    .map(Bug::getRejectedUser).toList();
            results.addAll(rejecters);
            results.addAll(fixers);
        }
        return results;
    }
}
