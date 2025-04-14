package io.github.xiaomisum.quickclick.job;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import io.github.xiaomisum.quickclick.dal.dataobject.project.ProjectMember;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Bug;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.BugExecRecord;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.PlanCase;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Testcase;
import io.github.xiaomisum.quickclick.dal.dataobject.report.TesterBasicData;
import io.github.xiaomisum.quickclick.dal.mapper.report.TesterBasicDataMapper;
import io.github.xiaomisum.quickclick.job.param.JobParam;
import io.github.xiaomisum.quickclick.service.project.ProjectMemberService;
import io.github.xiaomisum.quickclick.service.qualitycenter.bug.BugService;
import io.github.xiaomisum.quickclick.service.qualitycenter.plan.PlanCaseService;
import io.github.xiaomisum.quickclick.service.qualitycenter.testcase.TestcaseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.migoo.framework.common.util.json.JsonUtils;
import xyz.migoo.framework.quartz.core.handler.JobHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TesterDayDataJobHandler implements JobHandler {

    @Resource
    private ProjectMemberService projectMemberService;
    @Resource
    private PlanCaseService planCaseService;
    @Resource
    private TestcaseService testcaseService;
    @Resource
    private BugService bugService;
    @Resource
    private TesterBasicDataMapper testerBasicDataMapper;

    @Override
    public String execute(String param, Long jobLogId) throws Exception {
        // 1、 通过配置的 postId 获取项目成员列表数据
        var jobParam = JsonUtils.parseObject(param, JobParam.class);
        var testerGrouping = projectMemberService.loadByPost(jobParam.getPostId())
                .stream()
                .collect(Collectors.groupingBy(ProjectMember::getProjectId));

        var yesterday = LocalDate.now().minusDays(1);
        var startTime = yesterday.atStartOfDay();
        var endTime = LocalDateTime.of(yesterday, LocalTime.MAX);
        var date = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // 1、获取前一天创建的测试用例
        var testCase = testcaseService.loadTestCase(startTime, endTime);
        var projectCaseGrouping = testCase.stream().collect(Collectors.groupingBy(Testcase::getProjectId));
        // 2、获取执行时间为前一天的测试用例
        var planCases = planCaseService.loadExecutedCase(startTime, endTime);
        // 按项目分组 测试用例
        var projectPlanCaseGrouping = planCases.stream().collect(Collectors.groupingBy(PlanCase::getProjectId));

        List<TesterBasicData> results = Lists.newArrayList();
        for (String projectId : testerGrouping.keySet()) {
            var testers = testerGrouping.get(projectId).stream().map(ProjectMember::getUserId).toList();
            if (CollectionUtil.isEmpty(testers)) {
                continue;
            }
            // 创建的测试用例
            var projectTestcases = projectCaseGrouping.get(projectId);
            // 执行人员用例分组
            var testProjectTestcases = Objects.isNull(projectTestcases) ? new HashMap<Long, List<Testcase>>() :
                    projectTestcases.stream().collect(Collectors.groupingBy(Testcase::getSupervisor));
            // 获取该测试人员的测试用例数量
            // 项目下的测试用例
            var projectPlanTestcases = projectPlanCaseGrouping.get(projectId);
            var executor = Objects.isNull(projectPlanTestcases) ? new HashMap<Long, List<PlanCase>>() :
                    projectPlanTestcases.stream().collect(Collectors.groupingBy(PlanCase::getExecutor));
            // 获取创建的缺陷
            var bugs1 = bugService.loadProjectBugByCreator(projectId, testers, startTime, endTime);
            var creatorGrouping = bugs1.stream().collect(Collectors.groupingBy(Bug::getCreatorId));
            // 获取关闭记录
            var bugs2 = bugService.loadProjectClosedRecords(projectId, testers, startTime, endTime);
            var closeGrouping = bugs2.stream().collect(Collectors.groupingBy(BugExecRecord::getUserId));
            // 获取激活记录
            var bugs3 = bugService.loadProjectReopenRecords(projectId, testers, startTime, endTime);
            // 测试人员的激活Bug分组
            var reopenGrouping = bugs3.stream().collect(Collectors.groupingBy(BugExecRecord::getUserId));
            // 遍历项目下的测试人员
            testers.forEach(userId -> {
                var data = new TesterBasicData().setDate(date).setUserId(userId).setProjectId(projectId)
                        // 创建的用例数量
                        .setTestcaseTotal(getSize(testProjectTestcases.get(userId)))
                        // 执行的用例数量
                        .setExecuteTestcaseTotal(getSize(executor.get(userId)))
                        // 获取创建的Bug数量
                        .setNewBugTotal(getSize(creatorGrouping.get(userId)))

                        // 关闭的Bug数量
                        .setClosedBugTotal(getSize(closeGrouping.get(userId)))
                        .setReopenedBugTotal(getSize(reopenGrouping.get(userId)));
                // 验证Bug总数 = 关闭数量+激活数量
                data.setValidatedBugTotal(data.getClosedBugTotal() + data.getReopenedBugTotal());
                results.add(data);
            });
        }
        if (!results.isEmpty()) {
            testerBasicDataMapper.insertBatch(results);
        }
        return "success";
    }

    private int getSize(List<?> items) {
        return Objects.isNull(items) ? 0 : items.size();
    }

}
