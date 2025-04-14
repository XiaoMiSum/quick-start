package io.github.xiaomisum.quickclick.job;

import cn.hutool.core.collection.CollectionUtil;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Review;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.ReviewCase;
import io.github.xiaomisum.quickclick.enums.TestStatus;
import io.github.xiaomisum.quickclick.job.param.JobParam;
import io.github.xiaomisum.quickclick.service.qualitycenter.review.ReviewCaseService;
import io.github.xiaomisum.quickclick.service.qualitycenter.review.ReviewService;
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
 * 根据用例评审情况更新测试评审的状态
 */
@Component
@Slf4j
public class ReviewStatusJobHandler implements JobHandler {

    private volatile LocalDateTime maxUpdateTime;

    @Resource
    private ReviewService reviewService;
    @Resource
    private ReviewCaseService caseService;

    @Override
    public String execute(String param, Long jobLogId) throws Exception {
        log.info("[任务开始] 根据用例执行情况更新测试评审的状态");
        JobParam jobParam = JsonUtils.parseObject(param, JobParam.class);
        // 1、获取有跟新的评审用例关联的评审id
        List<String> reviewIds = caseService.loadReviewId(maxUpdateTime, jobParam.getOffset());
        if (CollectionUtil.isNotEmpty(reviewIds)) {
            log.info("获取到用例更新时间 {} 的测试评审 {}", maxUpdateTime, reviewIds.size());
            reviewIds.forEach(item -> {
                // 根据评审编号获取所有评审用例
                Review review = reviewService.get(item);
                List<ReviewCase> cases = caseService.getList(item);
                Map<TestStatus, List<ReviewCase>> group = cases.stream()
                        .collect(Collectors.groupingBy(ReviewCase::getResult));
                List<ReviewCase> preparing = Preparing.get(group);
                List<ReviewCase> passed = Passed.get(group);
                List<ReviewCase> failed = Failed.get(group);
                List<ReviewCase> blocking = Blocking.get(group);
                List<ReviewCase> skipped = Skipped.get(group);
                List<ReviewCase> processing = Processing.get(group);
                if (preparing.isEmpty() && processing.isEmpty() && !review.getStatus().equals(Finished)) {
                    // 进行中和未开始的都为空，评审完成
                    log.info("没有【preparing】【processing】的用例，更新状态为【Finished】");
                    reviewService.updateStatus(item, Finished);
                } else if (preparing.size() == cases.size() && !review.getStatus().equals(Preparing)) {
                    // 准备中的数量与规划的测试用例总数一致，准备中
                    log.info("【preparing】的用例数量与规划用例总数一致，更新状态为【Preparing】");
                    reviewService.updateStatus(item, Preparing);
                } else if ((passed.size() + failed.size() + blocking.size() + skipped.size() + processing.size()) < cases.size()
                        && !review.getStatus().equals(Processing)) {
                    // 成功数量 + 失败数量 + 阻塞数量 + 跳过数量 + 进行中数量 之和 小于 总数，评审进行中
                    log.info("除【preparing】之外的用例数量总和小于规划用例总数，更新状态为【Processing】");
                    reviewService.updateStatus(item, Processing);
                }
            });
        }
        // 设置更新时间为当前时间
        maxUpdateTime = LocalDateTime.now();
        return "success";
    }

}
