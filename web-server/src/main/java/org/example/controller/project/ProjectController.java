package org.example.controller.project;

import jakarta.annotation.Resource;
import org.example.controller.project.vo.ProjectAddReqVO;
import org.example.controller.project.vo.ProjectQueryReqVO;
import org.example.controller.project.vo.ProjectUpdateReqVO;
import org.example.convert.project.ProjectConvert;
import org.example.dal.dataobject.project.Project;
import org.example.service.project.ProjectService;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.common.pojo.SimpleData;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Resource
    private ProjectService service;

    @GetMapping
    public Result<?> getPage(ProjectQueryReqVO req) {
        return Result.getSuccessful(ProjectConvert.INSTANCE.convert(service.getPage(req)));
    }

    @GetMapping("/{id}")
    public Result<?> get(@PathVariable Long id) {
        return Result.getSuccessful(ProjectConvert.INSTANCE.convert(service.get(id)));
    }

    @PostMapping
    public Result<?> add(@RequestBody ProjectAddReqVO data) {
        service.add(ProjectConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @PutMapping
    public Result<?> update(@RequestBody ProjectUpdateReqVO data) {
        service.update(ProjectConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable Long id) {
        service.remove(id);
        return Result.getSuccessful();
    }

    @GetMapping("/simple")
    public Result<List<SimpleData<Long>>> getSimple() {
        // 获得用户列表，只要开启状态的
        List<Project> list = service.getList();
        return Result.getSuccessful(ProjectConvert.INSTANCE.convert(list));
    }
}
