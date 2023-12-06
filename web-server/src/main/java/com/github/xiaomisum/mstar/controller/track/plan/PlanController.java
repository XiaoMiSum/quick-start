/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021.  Lorem XiaoMiSum (mi_xiao@qq.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * 'Software'), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.xiaomisum.mstar.controller.track.plan;

import cn.hutool.core.util.StrUtil;
import com.github.xiaomisum.mstar.controller.track.plan.vo.*;
import com.github.xiaomisum.mstar.controller.track.testcase.vo.testcase.TestcaseQueryReqVO;
import com.github.xiaomisum.mstar.convert.track.PlanConvert;
import com.github.xiaomisum.mstar.convert.track.TestcaseConvert;
import com.github.xiaomisum.mstar.dal.dataobject.track.Plan;
import com.github.xiaomisum.mstar.dal.dataobject.track.PlanCase;
import com.github.xiaomisum.mstar.dal.dataobject.track.Testcase;
import com.github.xiaomisum.mstar.enums.ResultEnum;
import com.github.xiaomisum.mstar.model.dto.TestcaseDTO;
import com.github.xiaomisum.mstar.service.track.plan.PlanCaseService;
import com.github.xiaomisum.mstar.service.track.plan.PlanService;
import com.github.xiaomisum.mstar.service.track.review.ReviewCaseService;
import com.github.xiaomisum.mstar.service.track.testcase.NodeService;
import com.github.xiaomisum.mstar.service.track.testcase.TestcaseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.annotation.CurrentUser;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/track/plan")
public class PlanController {

    @Resource
    private PlanService service;
    @Resource
    private PlanCaseService caseService;
    @Resource
    private ReviewCaseService reviewCaseService;
    @Resource
    private TestcaseService testcaseService;
    @Resource
    private NodeService nodeService;

    @GetMapping
    public Result<?> getPage(@RequestHeader("x-project-id") String projectId, PlanQueryReqVO req) {
        // 获得测试计划分页列表
        req.setProjectId(projectId);
        PageResult<PlanPageRespVO> result = PlanConvert.INSTANCE.convert(service.getPage(req));
        result.getList().forEach(item -> item.setStatistics(caseService.statistics(item.getId())));
        return Result.getSuccessful(result);
    }

    @GetMapping("/{id}")
    public Result<?> get(@RequestHeader("x-project-id") String projectId, @PathVariable String id) {
        PlanRespVO result = PlanConvert.INSTANCE.convert(service.get(projectId, id));
        result.setStatistics(caseService.statistics(result.getId()));
        return Result.getSuccessful(result);
    }

    @PostMapping
    public Result<?> add(@RequestHeader("x-project-id") String projectId, @RequestBody Plan data) {
        data.setProjectId(projectId);
        return Result.getSuccessful(service.add(data));
    }

    @PutMapping
    public Result<?> update(@RequestHeader("x-project-id") String projectId, @RequestBody Plan data) {
        data.setProjectId(projectId);
        service.update(data);
        return Result.getSuccessful();
    }

    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable String id) {
        service.remove(id);
        return Result.getSuccessful();
    }

    @GetMapping("/case")
    public Result<?> getCasePage(PlanCaseQueryReqVO req) {
        PageResult<PlanCasePageRespVO> result = PlanConvert.INSTANCE.convert1(caseService.getPage(req));
        result.getList().forEach(item -> {
            if (item.getNodeId().length() > 10) {
                item.setPath(nodeService.get(item.getNodeId()).getPath());
            }
        });
        return Result.getSuccessful(result);
    }

    @PostMapping("/case/sync")
    public Result<?> syncReviewCase(@RequestHeader("x-project-id") String projectId,
                                    @RequestBody PlanCaseExecuteVO req) {
        TestcaseDTO testcase = TestcaseConvert.INSTANCE.convert(testcaseService.get(req.getCaseId()));
        PlanCase planCase = PlanConvert.INSTANCE.convert(testcase);
        planCase.setPlanId(req.getPlanId()).setId(req.getId());
        caseService.update(planCase);
        return Result.getSuccessful(planCase);
    }

    @GetMapping("/case/execute")
    public Result<?> getPlanCase(String id) {
        PlanCaseRespVO result = PlanConvert.INSTANCE.convert(caseService.get(id));
        if (Objects.nonNull(result)) {
            if (result.getNodeId().length() > 10) {
                result.setPath(nodeService.get(result.getNodeId()).getPath());
            }
        }
        return Result.getSuccessful(result);
    }

    @PostMapping("/case/execute")
    public Result<?> reviewCase(@CurrentUser LoginUser user, @RequestBody PlanCaseExecuteVO execute) {
        execute.setExecutor(user.getName());
        // 设置实际开始时间
        List<PlanCase> total = caseService.getList(execute.getPlanId());
        List<PlanCase> notStart = caseService.getList(execute.getPlanId(), ResultEnum.NOTSTARTED);
        if (total.size() == notStart.size()) {
            service.setStartTime(execute.getPlanId());
        }
        caseService.execute(execute);
        testcaseService.update((Testcase) new Testcase().setReviewed(execute.getResult()).setId(execute.getId()));
        List<PlanCase> notStart2 = caseService.getList(execute.getPlanId(), ResultEnum.NOTSTARTED);
        // 设置实际结束时间
        if (notStart2.isEmpty()) {
            service.setEndTime(execute.getPlanId());
        }
        return Result.getSuccessful();
    }

    @GetMapping("/case/first")
    public Result<?> getFirstReviewCase(String reviewId) {
        List<PlanCase> results = caseService.getList(reviewId);
        if (results.isEmpty()) {
            return Result.getSuccessful(null);
        }
        return Result.getSuccessful(results.get(0).getId());
    }

    @GetMapping("/case/{opt}")
    public Result<?> getReviewCase(String planId, String id, @PathVariable String opt) {
        List<PlanCase> results = caseService.getListGtId(opt, planId, id);
        if (results.isEmpty()) {
            return Result.getSuccessful(null);
        }
        PlanCaseRespVO result = PlanConvert.INSTANCE.convert(results.get(0));
        if (result.getNodeId().length() > 10) {
            result.setPath(nodeService.get(result.getNodeId()).getPath());
        }
        return Result.getSuccessful(result);
    }

    @GetMapping("/case/unassociated")
    public Result<?> getUnAssociatedCasePage(@RequestHeader("x-project-id") String projectId, PlanCaseQueryReqVO req) {
        List<String> ids = caseService.getList(req.getPlanId()).stream().map(PlanCase::getCaseId).toList();
        TestcaseQueryReqVO caseQuery = new TestcaseQueryReqVO(req.getPageNo(), req.getPageSize())
                .setProjectId(projectId)
                .setName(req.getCaseName())
                .setNodeId(req.getNodeId());
        PageResult<PlanCasePageRespVO> result = PlanConvert.INSTANCE.convert2(testcaseService.getPage(caseQuery, ids));
        result.getList().forEach(item -> {
            if (item.getNodeId().length() > 10) {
                item.setPath(nodeService.get(item.getNodeId()).getPath());
            }
        });
        return Result.getSuccessful(result);
    }

    @PostMapping("/case")
    public Result<?> addPlanCase(@RequestBody PlanCaseAddReqVO data) {
        List<TestcaseDTO> testcases = TestcaseConvert.INSTANCE.convert(testcaseService.getList(data.getTestcases()));
        List<PlanCase> list = PlanConvert.INSTANCE.convert(testcases);
        list.forEach(item -> item.setPlanId(data.getPlanId()));
        caseService.add(list);
        return Result.getSuccessful();
    }

    @PostMapping("/imports")
    public Result<?> importPlanCase(@RequestBody PlanCaseImportReqVO data) {
        List<String> caseIds = caseService.getList(data.getPlanId())
                .stream().map(PlanCase::getCaseId)
                .toList();
        List<PlanCase> list = PlanConvert.INSTANCE.convert1(
                reviewCaseService.getListNotInCaseIds(data.getReviewId(), caseIds), data.getPlanId()
        );
        list.forEach(item -> item.setPlanId(data.getPlanId()));
        caseService.add(list);
        return Result.getSuccessful(list.size());
    }

    @DeleteMapping("/case/{id}")
    public Result<?> unassociatedCase(@PathVariable String id) {
        caseService.remove(id);
        return Result.getSuccessful();
    }

    @DeleteMapping("/case")
    public Result<?> removeCase(String ids) {
        caseService.remove(StrUtil.split(ids, ",").stream().map(String::valueOf).toList());
        return Result.getSuccessful();
    }
}
