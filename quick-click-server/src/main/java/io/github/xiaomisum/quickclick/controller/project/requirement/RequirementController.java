package io.github.xiaomisum.quickclick.controller.project.requirement;

import io.github.xiaomisum.quickclick.controller.project.requirement.vo.*;
import io.github.xiaomisum.quickclick.convert.project.RequirementConvert;
import io.github.xiaomisum.quickclick.dal.dataobject.project.Requirement;
import io.github.xiaomisum.quickclick.service.project.RequirementService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@RestController
@RequestMapping("/project/requirement")
public class RequirementController {

    @Resource
    private RequirementService service;

    /**
     * 分页获取需求列表
     */
    @GetMapping("/page")
    public Result<PageResult<RequirementRespVO>> getPage(RequirementQueryReqVO reqVO) {
        PageResult<Requirement> pageResult = service.getPage(reqVO);
        return Result.getSuccessful(RequirementConvert.INSTANCE.convertPage(pageResult));
    }

    /**
     * 获取需求列表
     */
    @GetMapping
    public Result<List<RequirementRespVO>> getList(@RequestParam(required = false) String projectId) {
        List<Requirement> list = service.getList(projectId);
        return Result.getSuccessful(RequirementConvert.INSTANCE.convert(list));
    }

    /**
     * 根据ID获取需求详情
     */
    @GetMapping("/{id}")
    public Result<RequirementRespVO> get(@PathVariable String id) {
        Requirement requirement = service.get(id);
        return Result.getSuccessful(RequirementConvert.INSTANCE.convert(requirement));
    }

    /**
     * 创建需求
     */
    @PostMapping
    public Result<RequirementRespVO> add(@Valid @RequestBody RequirementAddReqVO reqVO) {
        Requirement requirement = RequirementConvert.INSTANCE.convert(reqVO);
        String id = service.add(requirement);
        requirement.setId(id);
        return Result.getSuccessful(RequirementConvert.INSTANCE.convert(requirement));
    }

    /**
     * 更新需求
     */
    @PutMapping
    public Result<RequirementRespVO> update(@Valid @RequestBody RequirementUpdateReqVO reqVO) {
        Requirement requirement = RequirementConvert.INSTANCE.convert(reqVO);
        service.update(requirement);
        return Result.getSuccessful(RequirementConvert.INSTANCE.convert(service.get(reqVO.getId())));
    }

    /**
     * 删除需求
     */
    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable String id) {
        service.remove(id);
        return Result.getSuccessful();
    }
}