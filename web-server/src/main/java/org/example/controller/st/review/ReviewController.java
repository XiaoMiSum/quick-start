package org.example.controller.st.review;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.example.controller.st.review.vo.*;
import org.example.controller.st.testcase.vo.TestcaseQueryReqVO;
import org.example.convert.st.ReviewConvert;
import org.example.convert.st.TestcaseConvert;
import org.example.dal.dataobject.st.ReviewCase;
import org.example.model.dto.TestcaseDTO;
import org.example.service.project.module.ModuleService;
import org.example.service.st.ReviewCaseService;
import org.example.service.st.ReviewService;
import org.example.service.st.TestcaseService;
import org.example.service.sys.user.UserService;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.annotation.CurrentUser;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/st/review")
public class ReviewController {

    @Resource
    private ReviewService service;

    @Resource
    private ReviewCaseService caseService;

    @Resource
    private TestcaseService testcaseService;

    @Resource
    private ModuleService moduleService;
    @Resource
    private UserService userService;

    @GetMapping
    public Result<?> getPage(@RequestHeader("x-project-id") Long projectId, ReviewQueryReqVO req) {
        // 获得测试用例分页列表
        req.setProjectId(projectId);
        PageResult<ReviewPageRespVO> result = ReviewConvert.INSTANCE.convert(service.getPage(req));
        result.getList().forEach(item -> {
            item.setSpeakUser(userService.get(item.getSpeaker()).getName());
        });
        // todo 这里要统计评审用例总数 评审通过率
        return Result.getSuccessful(result);
    }

    @GetMapping("/{id}")
    public Result<?> get(@PathVariable Long id) {
        return Result.getSuccessful(ReviewConvert.INSTANCE.convert(service.get(id)));
    }

    @PostMapping
    public Result<?> add(@RequestHeader("x-project-id") Long projectId, @RequestBody ReviewAddReqVO data) {
        data.setProjectId(projectId);
        return Result.getSuccessful(service.add(ReviewConvert.INSTANCE.convert(data)));
    }

    @PutMapping
    public Result<?> update(@RequestHeader("x-project-id") Long projectId, @RequestBody ReviewUpdateReqVO data) {
        data.setProjectId(projectId);
        service.update(ReviewConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable Long id) {
        service.remove(id);
        return Result.getSuccessful();
    }

    @GetMapping("/case")
    public Result<?> getCasePage(@RequestHeader("x-project-id") Long projectId, ReviewCaseQueryReqVO req) {
        PageResult<ReviewCasePageRespVO> result = ReviewConvert.INSTANCE.convert1(caseService.getPage(req));
        result.getList().forEach(item -> {
            if (item.getModuleId() > 0) {
                item.setPath(moduleService.get(projectId, item.getModuleId()).getPath());
            }
            if (Objects.nonNull(item.getChargeUserId())) {
                item.setChargeUser(userService.get(item.getChargeUserId()).getName());
            }
            if (Objects.nonNull(item.getReviewer())) {
                item.setReviewUser(userService.get(item.getReviewer()).getName());
            }
        });
        return Result.getSuccessful(result);
    }

    @GetMapping("/case/execute")
    public Result<?> getReviewCase(Long id) {
        ReviewCaseRespVO result = ReviewConvert.INSTANCE.convert(caseService.get(id));
        if (Objects.nonNull(result)) {
            if (result.getModuleId() > 0) {
                result.setPath(moduleService.get(result.getProjectId(), result.getModuleId()).getPath());
            }
            result.setChargeUser(userService.get(result.getChargeUserId()).getName());
        }
        return Result.getSuccessful(result);
    }

    @PostMapping("/case/execute")
    public Result<?> reviewCase(@CurrentUser LoginUser user, @RequestBody ReviewCaseExecuteVO execute) {
        execute.setReviewer(user.getId());
        caseService.reviewed(execute);
        return Result.getSuccessful(ReviewConvert.INSTANCE.convert(caseService.get(execute.getId())));
    }

    @GetMapping("/case/{opt}")
    public Result<?> getReviewCase(Long reviewId, Long id, @PathVariable String opt) {
        // todo 1、从头开始获取评审下的第一条用例 用于开始评审按钮
        List<ReviewCase> results = caseService.getListGtId(opt, reviewId, id);
        if (results.isEmpty()) {
            return Result.getSuccessful(null);
        }
        ReviewCaseRespVO result = ReviewConvert.INSTANCE.convert(results.get(0));
        if (result.getModuleId() > 0) {
            result.setPath(moduleService.get(result.getProjectId(), result.getModuleId()).getPath());
        }
        result.setChargeUser(userService.get(result.getChargeUserId()).getName());
        return Result.getSuccessful(result);
    }

    @GetMapping("/case/unassociated")
    public Result<?> getUnAssociatedCasePage(@RequestHeader("x-project-id") Long projectId, ReviewCaseQueryReqVO req) {
        List<Long> ids = caseService.getList(req.getReviewId()).stream().map(ReviewCase::getId).toList();
        TestcaseQueryReqVO caseQuery = new TestcaseQueryReqVO(req.getPageNo(), req.getPageSize())
                .setProjectId(projectId)
                .setName(req.getCaseName())
                .setModuleId(req.getModuleId());
        PageResult<ReviewCasePageRespVO> result = ReviewConvert.INSTANCE.convert2(testcaseService.getPage(caseQuery, ids));
        result.getList().forEach(item -> {
            if (item.getModuleId() > 0) {
                item.setPath(moduleService.get(projectId, item.getModuleId()).getPath());
            }
            item.setChargeUser(userService.get(item.getChargeUserId()).getName());
        });
        return Result.getSuccessful(result);
    }

    @PostMapping("/case")
    public Result<?> addReviewCase(@RequestHeader("x-project-id") Long projectId, @RequestBody ReviewCaseAddReqVO data) {
        List<TestcaseDTO> testcases = TestcaseConvert.INSTANCE.convert(testcaseService.getList(data.getTestcases()));
        List<ReviewCase> list = ReviewConvert.INSTANCE.convert(testcases);
        list.forEach(item -> item.setProjectId(projectId).setReviewId(data.getReviewId()));
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
