package org.example.controller.tracked.plan;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.example.controller.tracked.plan.vo.*;
import org.example.controller.tracked.testcase.vo.TestcaseQueryReqVO;
import org.example.convert.tracked.PlanConvert;
import org.example.convert.tracked.TestcaseConvert;
import org.example.dal.dataobject.tracked.Plan;
import org.example.dal.dataobject.tracked.PlanCase;
import org.example.dal.dataobject.tracked.Testcase;
import org.example.model.dto.TestcaseDTO;
import org.example.service.project.module.ModuleService;
import org.example.service.sys.user.UserService;
import org.example.service.tracked.PlanCaseService;
import org.example.service.tracked.PlanService;
import org.example.service.tracked.TestcaseService;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.annotation.CurrentUser;

import java.util.List;
import java.util.Objects;

import static org.example.enums.ResultEnum.NOTSTARTED;

@RestController
@RequestMapping("/tracked/plan")
public class PlanController {

    @Resource
    private PlanService service;
    @Resource
    private PlanCaseService caseService;
    @Resource
    private TestcaseService testcaseService;
    @Resource
    private ModuleService moduleService;
    @Resource
    private UserService userService;

    @GetMapping
    public Result<?> getPage(@RequestHeader("x-project-id") Long projectId, PlanQueryReqVO req) {
        // 获得测试计划分页列表
        req.setProjectId(projectId);
        PageResult<PlanPageRespVO> result = PlanConvert.INSTANCE.convert(service.getPage(req));
        result.getList().forEach(item -> {
            item.setExecutorUser(userService.get(item.getExecutor()).getName());
            item.setStatistics(caseService.statistics(item.getId()));
        });
        return Result.getSuccessful(result);
    }

    @GetMapping("/{id}")
    public Result<?> get(@RequestHeader("x-project-id") Long projectId, @PathVariable Long id) {
        PlanRespVO result = PlanConvert.INSTANCE.convert(service.get(projectId, id));
        result.setStatistics(caseService.statistics(result.getId()));
        return Result.getSuccessful(result);
    }

    @PostMapping
    public Result<?> add(@RequestHeader("x-project-id") Long projectId, @RequestBody Plan data) {
        data.setProjectId(projectId);
        return Result.getSuccessful(service.add(data));
    }

    @PutMapping
    public Result<?> update(@RequestHeader("x-project-id") Long projectId, @RequestBody Plan data) {
        data.setProjectId(projectId);
        service.update(data);
        return Result.getSuccessful();
    }

    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable Long id) {
        service.remove(id);
        return Result.getSuccessful();
    }

    @GetMapping("/case")
    public Result<?> getCasePage(@RequestHeader("x-project-id") Long projectId, PlanCaseQueryReqVO req) {
        PageResult<PlanCasePageRespVO> result = PlanConvert.INSTANCE.convert1(caseService.getPage(req));
        result.getList().forEach(item -> {
            if (item.getModuleId() > 0) {
                item.setPath(moduleService.get(projectId, item.getModuleId()).getPath());
            }
            if (Objects.nonNull(item.getChargeUserId())) {
                item.setChargeUser(userService.get(item.getChargeUserId()).getName());
            }
            if (Objects.nonNull(item.getExecutor())) {
                item.setExecutorUser(userService.get(item.getExecutor()).getName());
            }
        });
        return Result.getSuccessful(result);
    }

    @GetMapping("/case/execute")
    public Result<?> getReviewCase(Long id) {
        PlanCaseRespVO result = PlanConvert.INSTANCE.convert(caseService.get(id));
        if (Objects.nonNull(result)) {
            if (result.getModuleId() > 0) {
                result.setPath(moduleService.get(result.getProjectId(), result.getModuleId()).getPath());
            }
            result.setChargeUser(userService.get(result.getChargeUserId()).getName());
        }
        return Result.getSuccessful(result);
    }

    @PostMapping("/case/execute")
    public Result<?> reviewCase(@CurrentUser LoginUser user, @RequestBody PlanCaseExecuteVO execute) {
        execute.setExecutor(user.getId());
        // 设置实际开始时间
        List<PlanCase> total = caseService.getList(execute.getPlanId());
        List<PlanCase> notStart = caseService.getList(execute.getPlanId(), NOTSTARTED);
        if (total.size() == notStart.size()) {
            service.setStartTime(execute.getPlanId());
        }
        caseService.execute(execute);
        testcaseService.update(new Testcase().setId(execute.getId()).setReviewed(execute.getResult()));
        List<PlanCase> notStart2 = caseService.getList(execute.getPlanId(), NOTSTARTED);
        // 设置实际结束时间
        if (notStart2.isEmpty()) {
            service.setEndTime(execute.getPlanId());
        }
        return Result.getSuccessful();
    }

    @GetMapping("/case/first")
    public Result<?> getFirstReviewCase(Long reviewId) {
        List<PlanCase> results = caseService.getList(reviewId);
        if (results.isEmpty()) {
            return Result.getSuccessful(null);
        }
        return Result.getSuccessful(results.get(0).getCaseId());
    }

    @GetMapping("/case/{opt}")
    public Result<?> getReviewCase(Long planId, Long id, @PathVariable String opt) {
        List<PlanCase> results = caseService.getListGtId(opt, planId, id);
        if (results.isEmpty()) {
            return Result.getSuccessful(null);
        }
        PlanCaseRespVO result = PlanConvert.INSTANCE.convert(results.get(0));
        if (result.getModuleId() > 0) {
            result.setPath(moduleService.get(result.getProjectId(), result.getModuleId()).getPath());
        }
        result.setChargeUser(userService.get(result.getChargeUserId()).getName());
        return Result.getSuccessful(result);
    }

    @GetMapping("/case/unassociated")
    public Result<?> getUnAssociatedCasePage(@RequestHeader("x-project-id") Long projectId, PlanCaseQueryReqVO req) {
        List<Long> ids = caseService.getList(req.getPlanId()).stream().map(PlanCase::getId).toList();
        TestcaseQueryReqVO caseQuery = new TestcaseQueryReqVO(req.getPageNo(), req.getPageSize())
                .setProjectId(projectId)
                .setName(req.getCaseName())
                .setModuleId(req.getModuleId());
        PageResult<PlanCasePageRespVO> result = PlanConvert.INSTANCE.convert2(testcaseService.getPage(caseQuery, ids));
        result.getList().forEach(item -> {
            if (item.getModuleId() > 0) {
                item.setPath(moduleService.get(projectId, item.getModuleId()).getPath());
            }
            item.setChargeUser(userService.get(item.getChargeUserId()).getName());
        });
        return Result.getSuccessful(result);
    }

    @PostMapping("/case")
    public Result<?> addPlanCase(@RequestHeader("x-project-id") Long projectId, @RequestBody PlanCaseAddReqVO data) {
        List<TestcaseDTO> testcases = TestcaseConvert.INSTANCE.convert(testcaseService.getList(data.getTestcases()));
        List<PlanCase> list = PlanConvert.INSTANCE.convert(testcases);
        list.forEach(item -> item.setProjectId(projectId).setPlanId(data.getPlanId()));
        caseService.add(list);
        return Result.getSuccessful();
    }

    @DeleteMapping("/case/{id}")
    public Result<?> unassociatedCase(@PathVariable Long id) {
        caseService.remove(id);
        return Result.getSuccessful();
    }

    @DeleteMapping("/case")
    public Result<?> remove(String ids) {
        caseService.remove(StrUtil.split(ids, ",").stream().map(Long::valueOf).toList());
        return Result.getSuccessful();
    }
}
