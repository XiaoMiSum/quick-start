package io.github.xiaomisum.quickclick.job;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.github.xiaomisum.quickclick.dal.dataobject.project.ProjectMember;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Bug;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.BugExecRecord;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.PlanCase;
import io.github.xiaomisum.quickclick.dal.dataobject.report.DeveloperBasicData;
import io.github.xiaomisum.quickclick.dal.mapper.report.DeveloperBasicDataMapper;
import io.github.xiaomisum.quickclick.job.param.JobParam;
import io.github.xiaomisum.quickclick.service.project.ProjectMemberService;
import io.github.xiaomisum.quickclick.service.qualitycenter.bug.BugService;
import io.github.xiaomisum.quickclick.service.qualitycenter.plan.PlanCaseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.migoo.framework.common.util.json.JsonUtils;
import xyz.migoo.framework.quartz.core.handler.JobHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DeveloperDayDataJobHandler implements JobHandler {

    @Resource
    private ProjectMemberService projectMemberService;
    @Resource
    private PlanCaseService planCaseService;
    @Resource
    private BugService bugService;
    @Resource
    private DeveloperBasicDataMapper developerBasicDataMapper;

    @Override
    public String execute(String param, Long jobLogId) throws Exception {
        // 1、 通过配置的 postId 获取项目成员列表数据
        var jobParam = JsonUtils.parseObject(param, JobParam.class);
        var developerGrouping = projectMemberService.loadByPost(jobParam.getPostId())
                .stream()
                .collect(Collectors.groupingBy(ProjectMember::getProjectId));

        var yesterday = LocalDate.now().minusDays(1);
        var startTime = yesterday.atStartOfDay();
        var endTime = LocalDateTime.of(yesterday, LocalTime.MAX);
        var date = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // 2、获取执行时间为前一天的测试用例
        var planCases = planCaseService.loadExecutedCase(startTime, endTime);

        // 按项目分组 测试用例
        var projectCaseGrouping = planCases.stream().collect(Collectors.groupingBy(PlanCase::getProjectId));
        List<DeveloperBasicData> results = Lists.newArrayList();
        for (String projectId : developerGrouping.keySet()) {
            var developers = developerGrouping.get(projectId).stream().map(ProjectMember::getUserId).toList();
            if (CollectionUtil.isEmpty(developers)) {
                continue;
            }
            // 项目下的测试用例
            var projectTestcases = projectCaseGrouping.get(projectId);
            Map<?, List<PlanCase>> backend;
            Map<?, List<PlanCase>> frontend;
            if (Objects.nonNull(projectTestcases)) {
                // 项目下的开发人员
                backend = projectTestcases.stream().collect(Collectors.groupingBy(PlanCase::getBackendDeveloper));
                frontend = projectTestcases.stream().collect(Collectors.groupingBy(PlanCase::getFrontendDeveloper));
            } else {
                frontend = Maps.newHashMap();
                backend = Maps.newHashMap();
            }

            // 获取当日新增Bug
            var bugs1 = bugService.loadProjectBugBySupervisor(projectId, developers, startTime, endTime);
            var supervisorGrouping = bugs1.stream().collect(Collectors.groupingBy(Bug::getSupervisor));
            // 获取当日修复记录
            var bugs2 = bugService.loadProjectFixedRecords(projectId, developers, startTime, endTime);
            var fixerGrouping = bugs2.stream().collect(Collectors.groupingBy(BugExecRecord::getUserId));
            // 获取项目下的有激活记录的Bug
            var bugs3 = bugService.loadProjectReopenBug(projectId, startTime, endTime);
            // 开发修复的激活Bug分组
            var fixerGroupingReopenBug = bugs3.stream().filter(item -> !Objects.isNull(item.getFixer()))
                    .collect(Collectors.groupingBy(Bug::getFixer));
            // 开发拒绝的激活Bug分组
            var rejectGroupingReopenBug = bugs3.stream().filter(item -> Objects.isNull(item.getFixer()))
                    .collect(Collectors.groupingBy(Bug::getRejectedUser));
            // 获取项目下的关闭的Bug数量
            var bugs4 = bugService.loadProjectCloseBug(projectId, startTime, endTime);
            var supervisorClosedGrouping = bugs4.stream().collect(Collectors.groupingBy(Bug::getSupervisor));
            // 遍历项目下的开发人员
            developers.forEach(userId -> {
                var data = new DeveloperBasicData().setDate(date).setUserId(userId).setProjectId(projectId);
                // 获取该开发人员的测试用例数量
                data.setTestcaseTotal(getSize(backend.get(userId)) + getSize(frontend.get(userId)));

                // 获取责任人的Bug数量
                data.setNewBugTotal(getSize(supervisorGrouping.get(userId)));

                // 获取关闭的Bug数量
                data.setClosedBugTotal(getSize(supervisorClosedGrouping.get(userId)));
                // 计算修复人的修复次数、修复时长
                var fixerBugs = fixerGrouping.get(userId);
                data.setFixedBugTotal(CollectionUtil.isEmpty(fixerBugs) ? 0 : fixerBugs.size());
                data.setFixedBugDuration(CollectionUtil.isEmpty(fixerBugs) ? 0 : fixerBugs.stream().mapToInt(BugExecRecord::getDuration).sum());

                // 获取 激活次数：按修复人 + 拒绝人
                var fixerReopenTotal = getSize(fixerGroupingReopenBug.get(userId));
                data.setReopenedBugTotal(getSize(rejectGroupingReopenBug.get(userId)) + fixerReopenTotal);
                // 所有数据总和大于零 才写入数据库
                if ((data.getTestcaseTotal() + data.getNewBugTotal() + data.getClosedBugTotal() +
                        data.getFixedBugTotal() + data.getFixedBugDuration() + data.getReopenedBugTotal()) > 0) {
                    results.add(data);
                }
            });
        }
        if (!results.isEmpty()) {
            developerBasicDataMapper.insertBatch(results);
        }
        return "success";
    }

    private int getSize(List<?> items) {
        return Objects.isNull(items) ? 0 : items.size();
    }

}
