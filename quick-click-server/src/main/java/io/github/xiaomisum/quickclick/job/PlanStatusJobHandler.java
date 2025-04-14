package io.github.xiaomisum.quickclick.job;

import cn.hutool.core.collection.CollectionUtil;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Plan;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.PlanCase;
import io.github.xiaomisum.quickclick.enums.TestStatus;
import io.github.xiaomisum.quickclick.job.param.JobParam;
import io.github.xiaomisum.quickclick.service.qualitycenter.plan.PlanCaseService;
import io.github.xiaomisum.quickclick.service.qualitycenter.plan.PlanService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.migoo.framework.common.util.json.JsonUtils;
import xyz.migoo.framework.quartz.core.handler.JobHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.github.xiaomisum.quickclick.enums.TestStatus.*;

/**
 * 根据用例执行情况更新测试计划的状态
 */
@Component
@Slf4j
public class PlanStatusJobHandler implements JobHandler {

    private volatile LocalDateTime maxUpdateTime;

    @Resource
    private PlanService planService;
    @Resource
    private PlanCaseService caseService;

    @Override
    public String execute(String param, Long jobLogId) throws Exception {
        log.info("[任务开始] 根据用例执行情况更新测试计划的状态");
        JobParam jobParam = JsonUtils.parseObject(param, JobParam.class);
        // 1、获取有跟新的计划用例关联的计划id
        List<String> planIds = caseService.loadPlanId(maxUpdateTime, jobParam.getOffset());
        if (CollectionUtil.isNotEmpty(planIds)) {
            log.info("获取到用例更新时间 {} 的测试计划 {}", maxUpdateTime, planIds.size());
            planIds.forEach(item -> {
                // 根据计划编号获取所有计划用例
                Plan plan = planService.get(item);
                List<PlanCase> cases = caseService.getList(item);
                Map<TestStatus, List<PlanCase>> group = cases.stream()
                        .collect(Collectors.groupingBy(PlanCase::getResult));
                List<PlanCase> preparing = Preparing.get(group);
                List<PlanCase> passed = Passed.get(group);
                List<PlanCase> failed = Failed.get(group);
                List<PlanCase> blocking = Blocking.get(group);
                List<PlanCase> skipped = Skipped.get(group);
                List<PlanCase> processing = Processing.get(group);
                if (preparing.isEmpty() && processing.isEmpty() && !plan.getStatus().equals(Finished)) {
                    // 进行中和未开始的都为空，完成
                    log.info("没有【preparing】【processing】的用例，更新状态为【Finished】");
                    planService.updateStatus(item, Finished);
                } else if (preparing.size() == cases.size() && !plan.getStatus().equals(Preparing)) {
                    // 准备中的数量与规划的测试用例总数一致，准备中
                    log.info("【preparing】的用例数量与规划用例总数一致，更新状态为【Preparing】");
                    planService.updateStatus(item, Preparing);
                } else if ((passed.size() + failed.size() + blocking.size() + skipped.size() + processing.size()) < cases.size()
                        && !plan.getStatus().equals(Preparing)) {
                    // 成功数量 + 失败数量 + 阻塞数量 + 跳过数量 + 进行中数量 之和 小于 总数，进行中
                    log.info("除【preparing】之外的用例数量总和小于规划用例总数，更新状态为【Processing】");
                    planService.updateStatus(item, Processing);
                }
            });
        }
        // 设置更新时间为当前时间
        maxUpdateTime = LocalDateTime.now();
        return "success";
    }
}
