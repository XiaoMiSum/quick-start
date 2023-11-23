package org.example.controller.project.link;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.example.controller.project.link.vo.LinkAddReqVO;
import org.example.controller.project.link.vo.LinkQueryReqVO;
import org.example.controller.project.link.vo.LinkUpdateReqVO;
import org.example.convert.project.LinkConvert;
import org.example.service.project.link.LinkService;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.Result;

@RestController
@RequestMapping("/project/link")
public class LinkController {

    @Resource
    private LinkService service;

    @GetMapping
    public Result<?> getPage(@RequestHeader("x-project-id") Long projectId, LinkQueryReqVO req) {
        req.setProjectId(projectId);
        return Result.getSuccessful(LinkConvert.INSTANCE.convert(service.getPage(req)));
    }

    @GetMapping("/{id}")
    public Result<?> get(@PathVariable Long id) {
        return Result.getSuccessful(LinkConvert.INSTANCE.convert(service.get(id)));
    }

    @PostMapping
    public Result<?> add(@RequestHeader("x-project-id") Long projectId, @RequestBody @Valid LinkAddReqVO data) {
        data.setProjectId(projectId);
        service.add(LinkConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @PutMapping
    public Result<?> update(@RequestBody @Valid LinkUpdateReqVO data) {
        service.update(LinkConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable Long id) {
        service.remove(id);
        return Result.getSuccessful();
    }
}
