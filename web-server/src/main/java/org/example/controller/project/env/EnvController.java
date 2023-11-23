package org.example.controller.project.env;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.example.controller.project.env.vo.EnvAddReqVO;
import org.example.controller.project.env.vo.EnvQueryReqVO;
import org.example.controller.project.env.vo.EnvUpdateReqVO;
import org.example.convert.project.EnvConvert;
import org.example.service.project.env.EnvService;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.Result;

@RestController
@RequestMapping("/project/env")
public class EnvController {

    @Resource
    private EnvService service;

    @GetMapping
    public Result<?> getPage(@RequestHeader("x-project-id") Long projectId, EnvQueryReqVO req) {
        req.setProjectId(projectId);
        return Result.getSuccessful(EnvConvert.INSTANCE.convert(service.getPage(req)));
    }

    @GetMapping("/{id}")
    public Result<?> get(@RequestHeader("x-project-id") Long projectId, @PathVariable Long id) {
        return Result.getSuccessful(EnvConvert.INSTANCE.convert(service.get(projectId, id)));
    }

    @PostMapping
    public Result<?> add(@RequestHeader("x-project-id") Long projectId, @RequestBody @Valid EnvAddReqVO data) {
        data.setProjectId(projectId);
        service.add(EnvConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @PutMapping
    public Result<?> update(@RequestBody @Valid EnvUpdateReqVO data) {
        service.update(EnvConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable Long id) {
        service.remove(id);
        return Result.getSuccessful();
    }
}
