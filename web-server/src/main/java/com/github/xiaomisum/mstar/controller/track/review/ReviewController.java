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

package com.github.xiaomisum.mstar.controller.track.review;

import cn.hutool.core.util.StrUtil;
import com.github.xiaomisum.mstar.controller.track.review.vo.*;
import com.github.xiaomisum.mstar.controller.track.testcase.vo.testcase.TestcaseQueryReqVO;
import com.github.xiaomisum.mstar.convert.track.ReviewConvert;
import com.github.xiaomisum.mstar.convert.track.TestcaseConvert;
import com.github.xiaomisum.mstar.dal.dataobject.track.ReviewCase;
import com.github.xiaomisum.mstar.dal.dataobject.track.Testcase;
import com.github.xiaomisum.mstar.model.dto.TestcaseDTO;
import com.github.xiaomisum.mstar.service.sys.user.UserService;
import com.github.xiaomisum.mstar.service.track.review.ReviewCaseService;
import com.github.xiaomisum.mstar.service.track.review.ReviewService;
import com.github.xiaomisum.mstar.service.track.testcase.NodeService;
import com.github.xiaomisum.mstar.service.track.testcase.TestcaseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.common.pojo.SimpleData;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.annotation.CurrentUser;

import java.util.List;
import java.util.Objects;

import static com.github.xiaomisum.mstar.enums.TestStatus.Prepare;

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
    @Resource
    private UserService userService;

    @GetMapping
    public Result<?> getPage(@RequestHeader("x-project-id") String projectId, ReviewQueryReqVO req) {
        // 获得测试用例分页列表
        req.setProjectId(projectId);
        PageResult<ReviewPageRespVO> result = ReviewConvert.INSTANCE.convert(service.getPage(req));
        result.getList().forEach(item -> item.setStatistics(caseService.statistics(item.getId())));
        return Result.getSuccessful(result);
    }

    @GetMapping("/{reviewId}")
    public Result<?> get(@RequestHeader("x-project-id") String projectId, @PathVariable String reviewId) {
        ReviewRespVO result = ReviewConvert.INSTANCE.convert(service.get(projectId, reviewId));
        if (Objects.nonNull(result)) {
            result.setStatistics(caseService.statistics(result.getId()));
        }
        return Result.getSuccessful(result);
    }

    @PostMapping
    public Result<?> add(@RequestHeader("x-project-id") String projectId, @RequestBody ReviewAddReqVO data) {
        data.setProjectId(projectId);
        return Result.getSuccessful(service.add(ReviewConvert.INSTANCE.convert(data)));
    }

    @PutMapping
    public Result<?> update(@RequestHeader("x-project-id") String projectId, @RequestBody ReviewUpdateReqVO data) {
        data.setProjectId(projectId);
        service.update(ReviewConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable String id) {
        service.remove(id);
        return Result.getSuccessful();
    }

    @GetMapping("/case")
    public Result<?> getCasePage(ReviewCaseQueryReqVO req) {
        PageResult<ReviewCasePageRespVO> result = ReviewConvert.INSTANCE.convert1(caseService.getPage(req));
        result.getList().forEach(item -> {
            if (item.getNodeId().length() > 10) {
                item.setPath(nodeService.get(item.getNodeId()).getPath());
            }
        });
        return Result.getSuccessful(result);
    }

    @PostMapping("/case/sync")
    public Result<?> syncReviewCase(@RequestBody ReviewCaseExecuteVO req) {
        TestcaseDTO testcase = TestcaseConvert.INSTANCE.convert(testcaseService.get(req.getCaseId()));
        ReviewCase reviewCase = ReviewConvert.INSTANCE.convert(testcase);
        reviewCase.setReviewId(req.getReviewId()).setId(req.getId());
        caseService.update(reviewCase);
        return Result.getSuccessful(reviewCase);
    }

    @GetMapping("/case/execute")
    public Result<?> getReviewCase(String id) {
        ReviewCaseRespVO result = ReviewConvert.INSTANCE.convert(caseService.get(id));
        if (Objects.nonNull(result)) {
            if (result.getNodeId().length() > 10) {
                result.setPath(nodeService.get(result.getNodeId()).getPath());
            }
        }
        return Result.getSuccessful(result);
    }

    @PostMapping("/case/execute")
    public Result<?> reviewCase(@CurrentUser LoginUser user, @RequestBody ReviewCaseExecuteVO execute) {
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
    public Result<?> getReviewCaseStart(String reviewId) {
        List<ReviewCase> results = caseService.getList(reviewId);
        if (results.isEmpty()) {
            return Result.getSuccessful(null);
        }
        return Result.getSuccessful(results.get(0).getId());
    }

    @GetMapping("/case/{opt}")
    public Result<?> getReviewCase(String reviewId, String id, @PathVariable String opt) {
        List<ReviewCase> results = caseService.getListGtId(opt, reviewId, id);
        if (results.isEmpty()) {
            return Result.getSuccessful(null);
        }
        ReviewCaseRespVO result = ReviewConvert.INSTANCE.convert(results.get(0));
        if (result.getNodeId().length() > 10) {
            result.setPath(nodeService.get(result.getNodeId()).getPath());
        }
        return Result.getSuccessful(result);
    }

    @GetMapping("/case/unassociated")
    public Result<?> getUnAssociatedCasePage(@RequestHeader("x-project-id") String projectId, ReviewCaseQueryReqVO req) {
        List<String> ids = caseService.getList(req.getReviewId()).stream().map(ReviewCase::getCaseId).toList();
        TestcaseQueryReqVO caseQuery = new TestcaseQueryReqVO(req.getPageNo(), req.getPageSize())
                .setProjectId(projectId)
                .setName(req.getCaseName())
                .setNodeId(req.getNodeId());
        PageResult<ReviewCasePageRespVO> result = ReviewConvert.INSTANCE.convert2(testcaseService.getPage(caseQuery, ids));
        result.getList().forEach(item -> {
            if (item.getNodeId().length() > 10) {
                item.setPath(nodeService.get(item.getNodeId()).getPath());
            }
        });
        return Result.getSuccessful(result);
    }

    @PostMapping("/case")
    public Result<?> addReviewCase(@RequestBody ReviewCaseAddReqVO data) {
        List<TestcaseDTO> testcases = TestcaseConvert.INSTANCE.convert(testcaseService.getList(data.getTestcases()));
        List<ReviewCase> list = ReviewConvert.INSTANCE.convert(testcases);
        list.forEach(item -> item.setReviewId(data.getReviewId()));
        caseService.add(list);
        return Result.getSuccessful();
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

    @GetMapping("/simple")
    public Result<List<SimpleData<String>>> getSimple(@RequestHeader("x-project-id") String projectId) {
        // 获得用户列表，只要开启状态的
        return Result.getSuccessful(ReviewConvert.INSTANCE.convert3(service.getList(projectId)));
    }
}
