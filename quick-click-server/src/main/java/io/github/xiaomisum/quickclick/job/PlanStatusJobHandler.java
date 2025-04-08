package io.github.xiaomisum.quickclick.job;

import io.github.xiaomisum.quickclick.dal.dataobject.quality.PlanCase;
import io.github.xiaomisum.quickclick.dal.mapper.qualitycenter.PlanCaseMapper;
import io.github.xiaomisum.quickclick.dal.mapper.qualitycenter.PlanMapper;
import io.github.xiaomisum.quickclick.enums.TestStatus;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import xyz.migoo.framework.quartz.core.handler.JobHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.github.xiaomisum.quickclick.enums.TestStatus.*;

@Component
public class PlanStatusJobHandler implements JobHandler {

    @Resource
    private PlanMapper mapper;
    @Resource
    private PlanCaseMapper planCaseMapper;

    @Override
    public String execute(String param, Long jobLogId) throws Exception {
        mapper.selectByStatus(Preparing).forEach(item -> {
            List<PlanCase> cases = planCaseMapper.selectList(item.getId());
            Map<TestStatus, List<PlanCase>> group = cases.stream()
                    .collect(Collectors.groupingBy(PlanCase::getResult));
            List<PlanCase> preparing = Preparing.get(group);
            List<PlanCase> passed = Passed.get(group);
            List<PlanCase> failed = Failed.get(group);
            List<PlanCase> blocking = Blocking.get(group);
            List<PlanCase> skipped = Skipped.get(group);
            List<PlanCase> processing = Processing.get(group);
            if (preparing.isEmpty() && processing.isEmpty()) {
                // 进行中和未开始的都为空，执行完成
                mapper.updateStatus(item.getId(), Finished);
            } else if (preparing.size() == cases.size()) {
                // 准备中的数量与规划的测试用例总数一致，准备中
                mapper.updateStatus(item.getId(), Preparing);
            } else if ((passed.size() + failed.size() + blocking.size() + skipped.size() + processing.size()) < cases.size()) {
                // 成功数量 + 失败数量 + 阻塞数量 + 跳过数量 + 进行中数量 之和 小于 总数，执行进行中
                mapper.updateStatus(item.getId(), Processing);
            }
        });
        return "success";
    }
}
