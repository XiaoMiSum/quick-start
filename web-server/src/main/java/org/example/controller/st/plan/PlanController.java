package org.example.controller.st.plan;

import jakarta.annotation.Resource;
import org.example.controller.st.plan.vo.PlanCaseAddReqVO;
import org.example.controller.st.plan.vo.PlanCaseQueryReqVO;
import org.example.controller.st.plan.vo.PlanQueryReqVO;
import org.example.controller.st.testcase.vo.TestcaseQueryReqVO;
import org.example.convert.st.PlanConvert;
import org.example.convert.st.TestcaseConvert;
import org.example.dal.dataobject.st.Plan;
import org.example.dal.dataobject.st.PlanCase;
import org.example.model.dto.TestcaseDTO;
import org.example.service.st.PlanCaseService;
import org.example.service.st.PlanService;
import org.example.service.st.TestcaseService;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.Result;

import java.util.List;

@RestController
@RequestMapping("/st/plan")
public class PlanController {

    @Resource
    private PlanService service;

    @Resource
    private PlanCaseService caseService;

    @Resource
    private TestcaseService testcase;

    @GetMapping
    public Result<?> getPage(@RequestHeader("x-project-id") Long projectId, PlanQueryReqVO req) {
        // 获得测试计划分页列表
        req.setProjectId(projectId);
        return Result.getSuccessful(service.getPage(req));
    }

    @GetMapping("/{id}")
    public Result<?> get(@RequestHeader("x-project-id") Long projectId, @PathVariable Long id) {
        return Result.getSuccessful(service.get(projectId, id));
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
    public Result<?> getCasePage(PlanCaseQueryReqVO req) {
        return Result.getSuccessful(caseService.getPage(req));
    }

    @GetMapping("/case/unassociated")
    public Result<?> getUnAssociatedCasePage(@RequestHeader("x-project-id") Long projectId, PlanCaseQueryReqVO req) {
        List<Long> ids = caseService.getList(req.getPlanId()).stream().map(PlanCase::getId).toList();
        TestcaseQueryReqVO caseQuery = new TestcaseQueryReqVO(req.getPageNo(), req.getPageSize())
                .setProjectId(projectId)
                .setName(req.getCaseName())
                .setModuleId(req.getModuleId());
        return Result.getSuccessful(TestcaseConvert.INSTANCE.convert(testcase.getPage(caseQuery, ids)));
    }

    @PostMapping("/case")
    public Result<?> addPlanCase(@RequestHeader("x-project-id") Long projectId, @RequestBody PlanCaseAddReqVO data) {
        List<TestcaseDTO> testcases = TestcaseConvert.INSTANCE.convert(testcase.getList(data.getTestcases()));
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
}
