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

package io.github.xiaomisum.quickclick.controller.quality.review;

import cn.hutool.core.util.StrUtil;
import io.github.xiaomisum.quickclick.controller.quality.review.vo.*;
import io.github.xiaomisum.quickclick.controller.quality.testcase.vo.TestcaseQueryReqVO;
import io.github.xiaomisum.quickclick.convert.qualitycenter.ReviewConvert;
import io.github.xiaomisum.quickclick.convert.qualitycenter.TestcaseConvert;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.ReviewCase;
import io.github.xiaomisum.quickclick.model.dto.TestcaseDTO;
import io.github.xiaomisum.quickclick.service.project.NodeService;
import io.github.xiaomisum.quickclick.service.qualitycenter.review.ReviewCaseService;
import io.github.xiaomisum.quickclick.service.qualitycenter.review.ReviewService;
import io.github.xiaomisum.quickclick.service.qualitycenter.testcase.TestcaseService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.exception.ErrorCode;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.common.pojo.SimpleData;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.annotation.CurrentUser;

import java.util.List;
import java.util.Objects;

import static io.github.xiaomisum.quickclick.enums.TestStatus.Prepare;

/**
 * 测试评审
 */
@RestController
@RequestMapping("/quality-center/review")
public class ReviewController {

    @Resource
    private ReviewService service;
    @Resource
    private ReviewCaseService reviewCaseService;
    @Resource
    private TestcaseService testcaseService;
    @Resource
    private NodeService nodeService;

    /**
     * 评审列表
     *
     * @param req 查询条件
     * @return 评审列表
     */
    @GetMapping
    public Result<?> getPage(ReviewQueryReqVO req) {
        // 获得测试用例分页列表
        PageResult<ReviewPageRespVO> result = ReviewConvert.INSTANCE.convert(service.getPage(req));
        result.getList().forEach(item -> item.setStatistics(reviewCaseService.statistics(item.getId())));
        return Result.getSuccessful(result);
    }

    /**
     * 评审明细
     *
     * @param reviewId 评审编号
     * @return 评审明细
     */
    @GetMapping("/{reviewId}")
    public Result<?> get(@PathVariable String reviewId) {
        ReviewRespVO result = ReviewConvert.INSTANCE.convert(service.get(reviewId));
        if (Objects.nonNull(result)) {
            result.setStatistics(reviewCaseService.statistics(result.getId()));
        }
        return Result.getSuccessful(result);
    }

    /**
     * 新增评审
     *
     * @param data 评审信息
     * @return 处理结果
     */
    @PostMapping
    public Result<?> add(@RequestBody @Valid ReviewAddReqVO data) {
        return Result.getSuccessful(service.add(ReviewConvert.INSTANCE.convert(data)));
    }

    /**
     * 更新评审
     *
     * @param data 评审信息
     * @return 处理结果
     */
    @PutMapping
    public Result<?> update(@RequestBody @Valid ReviewUpdateReqVO data) {
        service.update(ReviewConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    /**
     * 删除评审
     *
     * @param id 评审编号
     * @return 处理结果
     */
    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable String id) {
        service.remove(id);
        return Result.getSuccessful();
    }

    /**
     * 评审明细
     *
     * @param req 查询条件
     * @return 用例明细
     */
    @GetMapping("/case")
    public Result<?> getCasePage(ReviewCaseQueryReqVO req) {
        PageResult<ReviewCasePageRespVO> result = ReviewConvert.INSTANCE.convert1(reviewCaseService.getPage(req));
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
     * 将评审中的用例副本更新为测试用例最新版本
     *
     * @param req 请求参数
     * @return 处理结果
     */
    @PostMapping("/case/sync")
    public Result<?> syncReviewCase(@RequestBody @Valid ReviewCaseSyncVO req) {
        TestcaseDTO testcase = TestcaseConvert.INSTANCE.convert(testcaseService.get(req.getOriginalId()));
        if (Objects.isNull(testcase)) {
            return Result.getError(new ErrorCode(-1, "原始用例不存在"));
        }
        ReviewCase reviewCase = ReviewConvert.INSTANCE.convert(testcase);
        reviewCase.setReviewId(req.getReviewId()).setId(req.getId());
        reviewCaseService.update(reviewCase);
        return Result.getSuccessful(reviewCase);
    }

    /**
     * 获取评审用例信息
     *
     * @param id 评审用例编号
     * @return 评审用例信息
     */
    @GetMapping("/case/{id}")
    public Result<?> getReviewCase(@PathVariable String id) {
        ReviewCaseRespVO result = ReviewConvert.INSTANCE.convert(reviewCaseService.get(id));
        if (Objects.nonNull(result)) {
            if (StrUtil.isNotBlank(result.getNodeId())) {
                result.setPath(nodeService.get(result.getNodeId()).getPath());
            }
        }
        return Result.getSuccessful(result);
    }

    /**
     * 评审用例
     *
     * @param execute 评审用例信息
     * @return 处理结果
     */
    @PostMapping("/case/execute")
    public Result<?> reviewCase(@CurrentUser LoginUser user,
                                @RequestBody @Valid ReviewCaseExecuteVO execute) {
        execute.setReviewer(user.getId());
        // 设置实际开始时间
        List<ReviewCase> total = reviewCaseService.getList(execute.getReviewId());
        List<ReviewCase> notStart = reviewCaseService.getList(execute.getReviewId(), Prepare);
        if (total.size() == notStart.size()) {
            service.setStartTime(execute.getReviewId());
        }
        reviewCaseService.reviewed(execute);
        testcaseService.update(ReviewConvert.INSTANCE.convert(execute));
        List<ReviewCase> prepares = reviewCaseService.getList(execute.getReviewId(), Prepare);
        // 设置实际结束时间
        if (prepares.isEmpty()) {
            service.setEndTime(execute.getReviewId());
        }
        return Result.getSuccessful();
    }

    /**
     * 获取评审明细首个用例
     *
     * @param reviewId 评审编号
     * @return 用例明细
     */
    @GetMapping("/case/first")
    public Result<?> getReviewCaseStart(@RequestParam String reviewId) {
        ReviewCase result = reviewCaseService.getFirst(reviewId);
        if (Objects.isNull(result)) {
            return Result.getSuccessful();
        }
        return Result.getSuccessful(result.getId());
    }

    /**
     * 上/下一条用例
     *
     * @param reviewId 评审编号
     * @param id       关联用例数据编号
     * @param opt      操作
     * @return 用例明细
     */
    @GetMapping("/case/{opt}")
    public Result<?> getReviewCase(@RequestParam String reviewId, @RequestParam Long id, @PathVariable String opt) {
        List<ReviewCase> results = reviewCaseService.getListGtId(opt, reviewId, id);
        if (results.isEmpty()) {
            return Result.getSuccessful(null);
        }
        ReviewCaseRespVO result = ReviewConvert.INSTANCE.convert(results.getFirst());
        if (StrUtil.isNotBlank(result.getNodeId())) {
            result.setPath(nodeService.get(result.getNodeId()).getPath());
        }
        return Result.getSuccessful(result);
    }

    /**
     * 获取未关联到当前评审的用例列表
     *
     * @param req 查询参数
     * @return 未关联到当前评审的用例列表
     */
    @GetMapping("/case/unassociated")
    public Result<?> getUnAssociatedCasePage(ReviewCaseQueryReqVO req) {
        List<String> ids = reviewCaseService.getList(req.getReviewId()).stream().map(ReviewCase::getOriginalId).toList();
        TestcaseQueryReqVO caseQuery = new TestcaseQueryReqVO(req.getPageNo(), req.getPageSize())
                .setTitle(req.getTitle())
                .setNodeId(req.getNodeId());
        PageResult<ReviewCasePageRespVO> result = ReviewConvert.INSTANCE.convert2(testcaseService.getPage(caseQuery, ids));
        result.getList().forEach(item -> {
            if (StrUtil.isNotBlank(item.getNodeId())) {
                item.setPath(nodeService.get(item.getNodeId()).getPath());
            }
        });
        return Result.getSuccessful(result);
    }

    /**
     * 关联用例到
     *
     * @param data 用例列表
     * @return 处理结果
     */
    @PostMapping("/case")
    public Result<?> addReviewCase(@RequestBody @Valid ReviewCaseAddReqVO data) {
        List<TestcaseDTO> testcases = TestcaseConvert.INSTANCE.convert(testcaseService.getList(data.getTestcases()));
        List<ReviewCase> list = ReviewConvert.INSTANCE.convert(testcases);
        list.forEach(item -> item.setReviewId(data.getReviewId()));
        reviewCaseService.add(list);
        return Result.getSuccessful();
    }

    /**
     * 取消关联用例
     *
     * @param ids 数据编号
     * @return 处理结果
     */
    @DeleteMapping("/case")
    public Result<?> removeCase(@RequestParam("ids") List<Long> ids) {
        reviewCaseService.remove(ids);
        return Result.getSuccessful();
    }

    @GetMapping("/simple")
    public Result<List<SimpleData>> getSimple(@RequestParam("projectId") String projectId) {
        return Result.getSuccessful(ReviewConvert.INSTANCE.convert3(service.getList(projectId)));
    }
}
