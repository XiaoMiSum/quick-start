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

package io.github.xiaomisum.quickclick.controller.qualitycenter.review;

import io.github.xiaomisum.quickclick.controller.qualitycenter.review.vo.*;
import io.github.xiaomisum.quickclick.controller.qualitycenter.testcase.vo.testcase.TestcaseQueryReqVO;
import io.github.xiaomisum.quickclick.convert.qualitycenter.ReviewConvert;
import io.github.xiaomisum.quickclick.convert.qualitycenter.TestcaseConvert;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.ReviewCase;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Testcase;
import io.github.xiaomisum.quickclick.model.dto.TestcaseDTO;
import io.github.xiaomisum.quickclick.service.qualitycenter.review.ReviewCaseService;
import io.github.xiaomisum.quickclick.service.qualitycenter.review.ReviewService;
import io.github.xiaomisum.quickclick.service.qualitycenter.testcase.NodeService;
import io.github.xiaomisum.quickclick.service.qualitycenter.testcase.TestcaseService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.common.pojo.SimpleData;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.annotation.CurrentUser;

import java.util.List;
import java.util.Objects;

import static io.github.xiaomisum.quickclick.enums.TestStatus.Prepare;

@RestController
@RequestMapping("/track/review")
public class ReviewController {

    @Resource
    private ReviewService service;

    @Resource
    private ReviewCaseService caseService;

    @Resource
    private TestcaseService testcaseService;
    @Resource
    private NodeService nodeService;

    @GetMapping
    public Result<?> getPage(ReviewQueryReqVO req) {
        // 获得测试用例分页列表
        PageResult<ReviewPageRespVO> result = ReviewConvert.INSTANCE.convert(service.getPage(req));
        result.getList().forEach(item -> item.setStatistics(caseService.statistics(item.getId())));
        return Result.getSuccessful(result);
    }

    @GetMapping("/{reviewId}")
    public Result<?> get(@PathVariable Long reviewId) {
        ReviewRespVO result = ReviewConvert.INSTANCE.convert(service.get(reviewId));
        if (Objects.nonNull(result)) {
            result.setStatistics(caseService.statistics(result.getId()));
        }
        return Result.getSuccessful(result);
    }

    @PostMapping
    public Result<?> add(@RequestBody @Valid ReviewAddReqVO data) {
        return Result.getSuccessful(service.add(ReviewConvert.INSTANCE.convert(data)));
    }

    @PutMapping
    public Result<?> update(@RequestBody @Valid ReviewUpdateReqVO data) {
        service.update(ReviewConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable Long id) {
        service.remove(id);
        return Result.getSuccessful();
    }

    @GetMapping("/case")
    public Result<?> getCasePage(ReviewCaseQueryReqVO req) {
        PageResult<ReviewCasePageRespVO> result = ReviewConvert.INSTANCE.convert1(caseService.getPage(req));
        result.getList().forEach(item -> {
            if (item.getNodeId() > 0) {
                item.setPath(nodeService.get(item.getNodeId()).getPath());
            }
        });
        return Result.getSuccessful(result);
    }

    @PostMapping("/case/sync")
    public Result<?> syncReviewCase(@RequestBody @Valid ReviewCaseSyncVO req) {
        TestcaseDTO testcase = TestcaseConvert.INSTANCE.convert(testcaseService.get(req.getCaseId()));
        ReviewCase reviewCase = ReviewConvert.INSTANCE.convert(testcase);
        reviewCase.setReviewId(req.getReviewId()).setId(req.getId());
        caseService.update(reviewCase);
        return Result.getSuccessful(reviewCase);
    }

    @GetMapping("/case/execute")
    public Result<?> getReviewCase(Long id) {
        ReviewCaseRespVO result = ReviewConvert.INSTANCE.convert(caseService.get(id));
        if (Objects.nonNull(result)) {
            if (result.getNodeId() > 10) {
                result.setPath(nodeService.get(result.getNodeId()).getPath());
            }
        }
        return Result.getSuccessful(result);
    }

    @PostMapping("/case/execute")
    public Result<?> reviewCase(@CurrentUser LoginUser user,
                                @RequestBody @Valid ReviewCaseExecuteVO execute) {
        execute.setReviewer(user.getName());
        // 设置实际开始时间
        service.setStartTime(execute.getReviewId());
        caseService.reviewed(execute);
        testcaseService.update((Testcase) new Testcase().setReviewed(execute.getResult()).setId(execute.getCaseId()));
        List<ReviewCase> prepares = caseService.getList(execute.getReviewId(), Prepare);
        // 设置实际结束时间
        if (prepares.isEmpty()) {
            service.setEndTime(execute.getReviewId());
        }
        return Result.getSuccessful();
    }

    @GetMapping("/case/first")
    public Result<?> getReviewCaseStart(Long reviewId) {
        List<ReviewCase> results = caseService.getList(reviewId);
        if (results.isEmpty()) {
            return Result.getSuccessful(null);
        }
        return Result.getSuccessful(results.get(0).getId());
    }

    @GetMapping("/case/{opt}")
    public Result<?> getReviewCase(Long reviewId, Long id, @PathVariable String opt) {
        List<ReviewCase> results = caseService.getListGtId(opt, reviewId, id);
        if (results.isEmpty()) {
            return Result.getSuccessful(null);
        }
        ReviewCaseRespVO result = ReviewConvert.INSTANCE.convert(results.get(0));
        if (result.getNodeId() > 0) {
            result.setPath(nodeService.get(result.getNodeId()).getPath());
        }
        return Result.getSuccessful(result);
    }

    @GetMapping("/case/unassociated")
    public Result<?> getUnAssociatedCasePage(ReviewCaseQueryReqVO req) {
        List<Long> ids = caseService.getList(req.getReviewId()).stream().map(ReviewCase::getCaseId).toList();
        TestcaseQueryReqVO caseQuery = new TestcaseQueryReqVO(req.getPageNo(), req.getPageSize())
                .setName(req.getCaseName())
                .setNodeId(req.getNodeId());
        PageResult<ReviewCasePageRespVO> result = ReviewConvert.INSTANCE.convert2(testcaseService.getPage(caseQuery, ids));
        result.getList().forEach(item -> {
            if (item.getNodeId() > 10) {
                item.setPath(nodeService.get(item.getNodeId()).getPath());
            }
        });
        return Result.getSuccessful(result);
    }

    @PostMapping("/case")
    public Result<?> addReviewCase(@RequestBody @Valid ReviewCaseAddReqVO data) {
        List<TestcaseDTO> testcases = TestcaseConvert.INSTANCE.convert(testcaseService.getList(data.getTestcases()));
        List<ReviewCase> list = ReviewConvert.INSTANCE.convert(testcases);
        list.forEach(item -> item.setReviewId(data.getReviewId()));
        caseService.add(list);
        return Result.getSuccessful();
    }

    @DeleteMapping("/case/{id}")
    public Result<?> unassociatedCase(@PathVariable Long id) {
        caseService.remove(id);
        return Result.getSuccessful();
    }

    @DeleteMapping("/case")
    public Result<?> removeCase(@RequestParam("ids") List<Long> ids) {
        caseService.remove(ids);
        return Result.getSuccessful();
    }

    @GetMapping("/simple")
    public Result<List<SimpleData>> getSimple(@RequestParam("projectId") Long projectId) {

        return Result.getSuccessful(ReviewConvert.INSTANCE.convert3(service.getList(projectId)));
    }
}
