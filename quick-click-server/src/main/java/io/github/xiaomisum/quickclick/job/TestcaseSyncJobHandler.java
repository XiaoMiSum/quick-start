package io.github.xiaomisum.quickclick.job;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import io.github.xiaomisum.quickclick.convert.qualitycenter.PlanCaseConvert;
import io.github.xiaomisum.quickclick.convert.qualitycenter.ReviewConvert;
import io.github.xiaomisum.quickclick.convert.qualitycenter.TestcaseConvert;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.PlanCase;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.ReviewCase;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Testcase;
import io.github.xiaomisum.quickclick.job.param.JobParam;
import io.github.xiaomisum.quickclick.model.dto.TestcaseDTO;
import io.github.xiaomisum.quickclick.service.qualitycenter.plan.PlanCaseService;
import io.github.xiaomisum.quickclick.service.qualitycenter.review.ReviewCaseService;
import io.github.xiaomisum.quickclick.service.qualitycenter.testcase.TestcaseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.migoo.framework.common.util.json.JsonUtils;
import xyz.migoo.framework.quartz.core.handler.JobHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 同步修改的测试用例到测试计划和测试评审
 */
@Component
@Slf4j
public class TestcaseSyncJobHandler implements JobHandler {

    private volatile LocalDateTime maxUpdateTime;
    @Resource
    private TestcaseService testcaseService;
    @Resource
    private PlanCaseService planCaseService;
    @Resource
    private ReviewCaseService reviewCaseService;

    @Override
    public String execute(String param, Long jobLogId) throws Exception {
        JobParam jobParam = JsonUtils.parseObject(param, JobParam.class);
        log.info("[任务开始] 同步修改的测试用例到测试计划和测试评审");
        // 加载有更新的测试用例
        List<Testcase> modifyList = testcaseService.loadIfUpdate(maxUpdateTime, jobParam.getOffset());
        log.info("获取到更新时间 {} 的测试用例 {}", maxUpdateTime, modifyList.size());
        if (CollectionUtil.isNotEmpty(modifyList)) {
            List<TestcaseDTO> testcases = TestcaseConvert.INSTANCE.convert(modifyList);
            Map<String, List<TestcaseDTO>> group = testcases.stream().collect(Collectors.groupingBy(TestcaseDTO::getOriginalId));
            Set<String> ids = group.keySet();
            // 获取测试计划关联的用例
            List<PlanCase> planCases = planCaseService.loadCaseByOriginalId(ids);
            log.info("待更新的测试计划关联用例数 {}", planCases.size());
            if (CollectionUtil.isNotEmpty(planCases)) {
                List<PlanCase> items = Lists.newArrayList();
                for (PlanCase planCase : planCases) {
                    // 设置新的数据对象
                    items.add(PlanCaseConvert.INSTANCE.convert(group.get(planCase.getOriginalId()).getFirst()));
                }
                planCaseService.updateBatch(items);
            }
            // 获取测试评审关联的用例
            List<ReviewCase> reviewCases = reviewCaseService.loadCaseByOriginalId(ids);
            log.info("待更新的测试评审关联用例数 {}", reviewCases.size());
            if (CollectionUtil.isNotEmpty(reviewCases)) {
                List<ReviewCase> items = Lists.newArrayList();
                for (ReviewCase reviewCase : reviewCases) {
                    // 设置新的数据对象
                    items.add(ReviewConvert.INSTANCE.convert(group.get(reviewCase.getOriginalId()).getFirst()));
                }
                reviewCaseService.updateBatch(items);
            }
        }
        maxUpdateTime = LocalDateTime.now();
        return "success";
    }
}
