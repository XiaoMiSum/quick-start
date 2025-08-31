package io.github.xiaomisum.quickclick.job;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import io.github.xiaomisum.quickclick.convert.qualitycenter.PlanCaseConvert;
import io.github.xiaomisum.quickclick.convert.qualitycenter.ReviewConvert;
import io.github.xiaomisum.quickclick.convert.qualitycenter.TestcaseConvert;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.CaseReuseRecord;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.PlanCase;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.ReviewCase;
import io.github.xiaomisum.quickclick.job.param.JobParam;
import io.github.xiaomisum.quickclick.model.dto.TestcaseDTO;
import io.github.xiaomisum.quickclick.service.qualitycenter.plan.PlanCaseService;
import io.github.xiaomisum.quickclick.service.qualitycenter.review.ReviewCaseService;
import io.github.xiaomisum.quickclick.service.qualitycenter.testcase.TestcaseService;
import io.github.xiaomisum.quickclick.service.qualitycenter.reuse.CaseReuseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.migoo.framework.common.util.json.JsonUtils;
import xyz.migoo.framework.quartz.core.handler.JobHandler;

import java.time.LocalDateTime;
import java.util.List;
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
    @Resource
    private CaseReuseService caseReuseService;

    @Override
    public String execute(String param, Long jobLogId) throws Exception {
        var jobParam = JsonUtils.parseObject(param, JobParam.class);
        log.info("[任务开始] 同步修改的测试用例到测试计划和测试评审");
        // 加载有更新的测试用例
        var modifyList = testcaseService.loadIfUpdate(maxUpdateTime, jobParam.getOffset());
        log.info("获取到更新时间 {} 的测试用例 {}", maxUpdateTime, modifyList.size());
        if (CollectionUtil.isNotEmpty(modifyList)) {
            var testcases = TestcaseConvert.INSTANCE.convert(modifyList);
            var group = testcases.stream().collect(Collectors.groupingBy(TestcaseDTO::getOriginalId));
            var ids = group.keySet();
            // 获取测试计划关联的用例
            var planCases = planCaseService.loadCaseByOriginalId(ids);
            log.info("待更新的测试计划关联用例数 {}", planCases.size());
            if (CollectionUtil.isNotEmpty(planCases)) {
                List<PlanCase> items = Lists.newArrayList();
                for (var item : planCases) {
                    // 设置新的数据对象
                    var data = PlanCaseConvert.INSTANCE.convert(group.get(item.getOriginalId()).getFirst())
                            .setId(item.getId());
                    items.add((PlanCase) data);
                }
                planCaseService.updateBatch(items);

                // 记录复用操作
                recordCaseReuse(planCases, "PLAN");
            }
            // 获取测试评审关联的用例
            var reviewCases = reviewCaseService.loadCaseByOriginalId(ids);
            log.info("待更新的测试评审关联用例数 {}", reviewCases.size());
            if (CollectionUtil.isNotEmpty(reviewCases)) {
                List<ReviewCase> items = Lists.newArrayList();
                for (var item : reviewCases) {
                    // 设置新的数据对象
                    var data = ReviewConvert.INSTANCE.convert(group.get(item.getOriginalId()).getFirst())
                            .setId(item.getId());
                    items.add((ReviewCase) data);
                }
                reviewCaseService.updateBatch(items);

                // 记录复用操作
                recordCaseReuse(reviewCases, "REVIEW");
            }
        }
        maxUpdateTime = LocalDateTime.now();
        return "success";
    }

    /**
     * 记录测试用例复用操作
     * 
     * @param cases      用例列表
     * @param targetType 目标类型
     */
    private void recordCaseReuse(List<?> cases, String targetType) {
        for (Object obj : cases) {
            CaseReuseRecord record = new CaseReuseRecord();
            if (obj instanceof PlanCase) {
                PlanCase planCase = (PlanCase) obj;
                record.setOriginalCaseId(planCase.getOriginalId())
                        .setTargetType(targetType)
                        .setTargetId(planCase.getPlanId())
                        .setOperatorId(-1L) // 系统自动同步，操作人ID设为-1
                        .setDescription("系统自动同步更新");
                caseReuseService.addRecord(record);
            } else if (obj instanceof ReviewCase) {
                ReviewCase reviewCase = (ReviewCase) obj;
                record.setOriginalCaseId(reviewCase.getOriginalId())
                        .setTargetType(targetType)
                        .setTargetId(reviewCase.getReviewId())
                        .setOperatorId(-1L) // 系统自动同步，操作人ID设为-1
                        .setDescription("系统自动同步更新");
                caseReuseService.addRecord(record);
            }
        }
        log.info("记录了{}条{}类型的用例复用操作", cases.size(), targetType);
    }
}