package org.example.controller.project.module;

import jakarta.annotation.Resource;
import org.example.controller.project.module.vo.ModuleAddReqVO;
import org.example.controller.project.module.vo.ModuleRespVO;
import org.example.controller.project.module.vo.ModuleUpdateReqVO;
import org.example.controller.sys.dept.vo.DeptSimpleRespVO;
import org.example.convert.project.ModuleConvert;
import org.example.dal.dataobject.project.ProjectModule;
import org.example.service.project.module.ModuleService;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.Result;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/project/module")
public class ModuleController {

    @Resource
    private ModuleService service;

    @GetMapping
    public Result<List<ModuleRespVO>> getPage(@RequestHeader("x-project-id") Long projectId) {
        // 获得用户分页列表
        List<ProjectModule> list = service.getList(projectId);
        list.sort(Comparator.comparing(ProjectModule::getSort));
        return Result.getSuccessful(ModuleConvert.INSTANCE.convert(list));
    }

    @GetMapping("/{id}")
    public Result<?> get(@RequestHeader("x-project-id") Long projectId, @PathVariable Long id) {
        return Result.getSuccessful(ModuleConvert.INSTANCE.convert(service.get(projectId, id)));
    }

    @PostMapping
    public Result<?> add(@RequestHeader("x-project-id") Long projectId, @RequestBody ModuleAddReqVO data) {
        data.setProjectId(projectId);
        service.add(ModuleConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @PutMapping
    public Result<?> update(@RequestHeader("x-project-id") Long projectId, @RequestBody ModuleUpdateReqVO data) {
        data.setProjectId(projectId);
        service.update(ModuleConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable Long id) {
        service.remove(id);
        return Result.getSuccessful();
    }

    @GetMapping("/simple")
    public Result<List<DeptSimpleRespVO>> getSimple(@RequestHeader("x-project-id") Long projectId) {
        // 获得用户列表，只要开启状态的
        List<ProjectModule> list = service.getList(projectId);
        return Result.getSuccessful(ModuleConvert.INSTANCE.convert1(list));
    }
}
