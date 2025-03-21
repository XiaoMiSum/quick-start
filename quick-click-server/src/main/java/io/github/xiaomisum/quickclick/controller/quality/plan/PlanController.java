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

package io.github.xiaomisum.quickclick.controller.quality.plan;

import cn.hutool.core.util.StrUtil;
import io.github.xiaomisum.quickclick.controller.quality.plan.vo.*;
import io.github.xiaomisum.quickclick.controller.quality.testcase.vo.TestcaseQueryReqVO;
import io.github.xiaomisum.quickclick.convert.qualitycenter.PlanCaseConvert;
import io.github.xiaomisum.quickclick.convert.qualitycenter.PlanConvert;
import io.github.xiaomisum.quickclick.convert.qualitycenter.TestcaseConvert;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.PlanCase;
import io.github.xiaomisum.quickclick.model.dto.TestcaseDTO;
import io.github.xiaomisum.quickclick.service.project.NodeService;
import io.github.xiaomisum.quickclick.service.qualitycenter.plan.PlanCaseService;
import io.github.xiaomisum.quickclick.service.qualitycenter.plan.PlanService;
import io.github.xiaomisum.quickclick.service.qualitycenter.review.ReviewCaseService;
import io.github.xiaomisum.quickclick.service.qualitycenter.testcase.TestcaseService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.annotation.CurrentUser;

import java.util.List;
import java.util.Objects;

import static io.github.xiaomisum.quickclick.enums.TestStatus.Preparing;

/**
 * 测试计划
 */
@RestController
@RequestMapping("/quality-center/plan")
public class PlanController {

    @Resource
    private PlanService service;
    @Resource
    private PlanCaseService planCaseService;
    @Resource
    private ReviewCaseService reviewCaseService;
    @Resource
    private TestcaseService testcaseService;
    @Resource
    private NodeService nodeService;

    /**
     * 计划列表
     *
     * @param req 查询条件
     * @return 计划列表
     */
    @GetMapping
    public Result<?> getPage(PlanQueryReqVO req) {
        // 获得测试计划分页列表
        PageResult<PlanPageRespVO> result = PlanConvert.INSTANCE.convert(service.getPage(req));
        result.getList().forEach(item -> item.setStatistics(planCaseService.statistics(item.getId())));
        return Result.getSuccessful(result);
    }

    /**
     * 计划详情
     *
     * @param planId 测试计划编号
     * @return 测试计划详情
     */
    @GetMapping("/{id}")
    public Result<?> get(@PathVariable("planId") String planId) {
        PlanRespVO result = PlanConvert.INSTANCE.convert(service.get(planId));
        if (Objects.nonNull(result)) {
            result.setStatistics(planCaseService.statistics(result.getId()));
        }
        return Result.getSuccessful(result);
    }

    /**
     * 新增计划
     *
     * @param data 计划数据
     * @return 处理结果
     */
    @PostMapping
    public Result<?> add(@RequestBody @Valid PlanAddReqVO data) {
        return Result.getSuccessful(service.add(PlanConvert.INSTANCE.convert(data)));
    }

    /**
     * 更新计划
     *
     * @param data 计划数据
     * @return 处理结果
     */
    @PutMapping
    public Result<?> update(@RequestBody @Valid PlanUpdateReqVO data) {
        service.update(PlanConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    /**
     * 删除测试计划
     *
     * @param id 计划编号
     * @return 处理结果
     */
    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable("id") String id) {
        service.remove(id);
        return Result.getSuccessful();
    }

    /**
     * 获取计划关联用例列表
     *
     * @param req 查询条件
     * @return 测试计划关联用例列表
     */
    @GetMapping("/case")
    public Result<?> getCasePage(PlanCaseQueryReqVO req) {
        PageResult<PlanCasePageRespVO> result = PlanCaseConvert.INSTANCE.convert(planCaseService.getPage(req));
        result.getList().forEach(item -> {
            if (StrUtil.isNotBlank(item.getNodeId())) {
                item.setPath(nodeService.get(item.getNodeId()).getPath());
            }
        });
        return Result.getSuccessful(result);
    }


    /**
     * 同步用例
     * <p>
     * 将计划中的用例副本更新为测试用例最新版本
     *
     * @param req 请求参数
     * @return 处理结果
     */
    @PostMapping("/case/sync")
    public Result<?> syncReviewCase(@RequestBody @Valid PlanCaseSyncVO req) {
        TestcaseDTO testcase = TestcaseConvert.INSTANCE.convert(testcaseService.get(req.getOriginalId()));
        PlanCase planCase = PlanCaseConvert.INSTANCE.convert(testcase);
        planCase.setPlanId(req.getPlanId()).setId(req.getId());
        planCaseService.update(planCase);
        return Result.getSuccessful(planCase);
    }

    /**
     * 获取执行用例信息
     *
     * @param id 执行用例数据编号
     * @return 执行用例信息
     */
    @GetMapping("/case/{id}")
    public Result<?> getPlanCase(@PathVariable("id") Long id) {
        PlanCaseRespVO result = PlanCaseConvert.INSTANCE.convert(planCaseService.get(id));
        if (Objects.nonNull(result)) {
            if (StrUtil.isNotBlank(result.getNodeId())) {
                result.setPath(nodeService.get(result.getNodeId()).getPath());
            }
        }
        return Result.getSuccessful(result);
    }

    /**
     * 执行用例
     *
     * @param execute 评审用例信息
     * @return 处理结果
     */
    @PostMapping("/case/execute")
    public Result<?> reviewCase(@CurrentUser LoginUser user, @RequestBody @Valid PlanCaseExecuteVO execute) {
        execute.setExecutor(user.getId());
        // 设置实际开始时间
        List<PlanCase> total = planCaseService.getList(execute.getPlanId());
        List<PlanCase> notStart = planCaseService.getList(execute.getPlanId(), Preparing);
        if (total.size() == notStart.size()) {
            service.setStartTime(execute.getPlanId());
        }
        planCaseService.execute(execute);
        List<PlanCase> prepares = planCaseService.getList(execute.getPlanId(), Preparing);
        // 设置实际结束时间
        if (prepares.isEmpty()) {
            service.setEndTime(execute.getPlanId());
        }
        return Result.getSuccessful();
    }

    /**
     * 获取计划明细首个用例
     *
     * @param reviewId 计划编号
     * @return 用例明细
     */
    @GetMapping("/case/first")
    public Result<?> getFirstReviewCase(@RequestParam("reviewId") String reviewId) {
        PlanCase results = planCaseService.getFirst(reviewId);
        if (Objects.isNull(results)) {
            return Result.getSuccessful(null);
        }
        return Result.getSuccessful(results.getId());
    }

    /**
     * 上/下一条用例
     *
     * @param planId 计划编号
     * @param id     关联用例数据编号
     * @param opt    操作
     * @return 用例明细
     */
    @GetMapping("/case/{opt}")
    public Result<?> getReviewCase(@RequestParam("planId") String planId,
                                   @RequestParam("id") Long id,
                                   @PathVariable("opt") String opt) {
        List<PlanCase> results = planCaseService.getListGtId(opt, planId, id);
        if (results.isEmpty()) {
            return Result.getSuccessful(null);
        }
        PlanCaseRespVO result = PlanCaseConvert.INSTANCE.convert(results.getFirst());
        if (StrUtil.isNotBlank(result.getNodeId())) {
            result.setPath(nodeService.get(result.getNodeId()).getPath());
        }
        return Result.getSuccessful(result);
    }

    /**
     * 获取未关联到当前计划的用例列表
     *
     * @param req 查询参数
     * @return 未关联到当前计划的用例列表
     */
    @GetMapping("/case/unassociated")
    public Result<?> getUnAssociatedCasePage(PlanCaseQueryReqVO req) {
        List<String> ids = planCaseService.getList(req.getPlanId()).stream().map(PlanCase::getOriginalId).toList();
        TestcaseQueryReqVO caseQuery = new TestcaseQueryReqVO(req.getPageNo(), req.getPageSize())
                .setTitle(req.getTitle())
                .setNodeId(req.getNodeId());
        PageResult<PlanCasePageRespVO> result = PlanCaseConvert.INSTANCE.convert1(testcaseService.getPage(caseQuery, ids));
        result.getList().forEach(item -> {
            if (StrUtil.isNotBlank(item.getNodeId())) {
                item.setPath(nodeService.get(item.getNodeId()).getPath());
            }
        });
        return Result.getSuccessful(result);
    }

    /**
     * 关联用例
     *
     * @param data 用例列表
     * @return 处理结果
     */
    @PostMapping("/case")
    public Result<?> addPlanCase(@RequestBody @Valid PlanCaseAddReqVO data) {
        List<TestcaseDTO> testcases = TestcaseConvert.INSTANCE.convert(testcaseService.getList(data.getTestcases()));
        List<PlanCase> list = PlanCaseConvert.INSTANCE.convert(testcases);
        list.forEach(item -> item.setPlanId(data.getPlanId()));
        planCaseService.add(list);
        return Result.getSuccessful();
    }

    /**
     * 从评审中导入关联用例
     *
     * @param data 关联数据
     * @return 处理结果
     */
    @PostMapping("/imports")
    public Result<?> importPlanCase(@RequestBody @Valid PlanCaseImportReqVO data) {
        List<String> caseIds = planCaseService.getList(data.getPlanId())
                .stream().map(PlanCase::getOriginalId)
                .toList();
        List<PlanCase> list = PlanCaseConvert.INSTANCE.convert(
                reviewCaseService.getListNotInOriginalIds(data.getReviewId(), caseIds), data.getPlanId()
        );
        list.forEach(item -> item.setPlanId(data.getPlanId()));
        planCaseService.add(list);
        return Result.getSuccessful(list.size());
    }

    /**
     * 取消关联用例
     *
     * @param ids 数据编号
     * @return 处理结果
     */
    @DeleteMapping("/case")
    public Result<?> unassociatedCase(@RequestParam("ids") List<Long> ids) {
        planCaseService.remove(ids);
        return Result.getSuccessful();
    }
}
