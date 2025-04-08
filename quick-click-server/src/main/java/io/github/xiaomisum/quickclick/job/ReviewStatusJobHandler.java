package io.github.xiaomisum.quickclick.job;

import io.github.xiaomisum.quickclick.dal.dataobject.quality.ReviewCase;
import io.github.xiaomisum.quickclick.dal.mapper.qualitycenter.ReviewCaseMapper;
import io.github.xiaomisum.quickclick.dal.mapper.qualitycenter.ReviewMapper;
import io.github.xiaomisum.quickclick.enums.TestStatus;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import xyz.migoo.framework.quartz.core.handler.JobHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.github.xiaomisum.quickclick.enums.TestStatus.*;

@Component
public class ReviewStatusJobHandler implements JobHandler {

    @Resource
    private ReviewMapper mapper;
    @Resource
    private ReviewCaseMapper reviewCaseMapper;

    @Override
    public String execute(String param, Long jobLogId) throws Exception {
        mapper.selectByStatus(Preparing).forEach(item -> {
            List<ReviewCase> cases = reviewCaseMapper.selectList(item.getId());
            Map<TestStatus, List<ReviewCase>> group = cases.stream()
                    .collect(Collectors.groupingBy(ReviewCase::getResult));
            List<ReviewCase> preparing = Preparing.get(group);
            List<ReviewCase> passed = Passed.get(group);
            List<ReviewCase> failed = Failed.get(group);
            List<ReviewCase> blocking = Blocking.get(group);
            List<ReviewCase> skipped = Skipped.get(group);
            List<ReviewCase> processing = Processing.get(group);
            if (preparing.isEmpty() && processing.isEmpty()) {
                // 进行中和未开始的都为空，评审完成
                mapper.updateStatus(item.getId(), Finished);
            } else if (preparing.size() == cases.size()) {
                // 准备中的数量与规划的测试用例总数一致，准备中
                mapper.updateStatus(item.getId(), Preparing);
            } else if ((passed.size() + failed.size() + blocking.size() + skipped.size() + processing.size()) < cases.size()) {
                // 成功数量 + 失败数量 + 阻塞数量 + 跳过数量 + 进行中数量 之和 小于 总数，评审进行中
                mapper.updateStatus(item.getId(), Processing);
            }
        });
        return "success";
    }
}
