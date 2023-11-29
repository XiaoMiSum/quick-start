package org.example.controller.tracked.testcase;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.example.controller.tracked.testcase.vo.TestcaseAddReqVO;
import org.example.controller.tracked.testcase.vo.TestcasePageRespVO;
import org.example.controller.tracked.testcase.vo.TestcaseQueryReqVO;
import org.example.controller.tracked.testcase.vo.TestcaseUpdateReqVO;
import org.example.convert.tracked.TestcaseConvert;
import org.example.service.project.module.ModuleService;
import org.example.service.project.tag.TagService;
import org.example.service.sys.user.UserService;
import org.example.service.tracked.TestcaseService;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;

@RestController
@RequestMapping("/tracked/case")
public class CaseController {

    @Resource
    private TestcaseService service;
    @Resource
    private ModuleService moduleService;
    @Resource
    private UserService userService;
    @Resource
    private TagService tagService;

    @GetMapping
    public Result<?> getPage(@RequestHeader("x-project-id") Long projectId, TestcaseQueryReqVO req) {
        // 获得测试用例分页列表
        req.setProjectId(projectId);
        PageResult<TestcasePageRespVO> result = TestcaseConvert.INSTANCE.convert(service.getPage(req));
        result.getList().forEach(item -> {
            if (item.getModuleId() > 0) {
                item.setPath(moduleService.get(projectId, item.getModuleId()).getPath());
            }
            item.setChargeUser(userService.get(item.getChargeUserId()).getName());
        });
        return Result.getSuccessful(result);
    }

    @GetMapping("/{id}")
    public Result<?> get(@PathVariable Long id) {
        return Result.getSuccessful(TestcaseConvert.INSTANCE.convert1(service.get(id)));
    }

    @PostMapping
    public Result<?> add(@RequestHeader("x-project-id") Long projectId, @RequestBody TestcaseAddReqVO data) {
        data.setProjectId(projectId);
        Long id = service.add(TestcaseConvert.INSTANCE.convert(data));
        tagService.save(projectId, data.getTags());
        return Result.getSuccessful(id);
    }

    @PutMapping
    public Result<?> update(@RequestHeader("x-project-id") Long projectId, @RequestBody TestcaseUpdateReqVO data) {
        data.setProjectId(projectId);
        service.update(TestcaseConvert.INSTANCE.convert(data));
        tagService.save(projectId, data.getTags());
        return Result.getSuccessful();
    }

    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable Long id) {
        service.remove(id);
        return Result.getSuccessful();
    }

    @DeleteMapping
    public Result<?> remove(String ids) {
        service.remove(StrUtil.split(ids, ",").stream().map(Long::valueOf).toList());
        return Result.getSuccessful();
    }
}
