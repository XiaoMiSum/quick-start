package org.example.controller.project.tag;

import jakarta.annotation.Resource;
import org.example.controller.project.tag.vo.TagAddReqVO;
import org.example.controller.project.tag.vo.TagQueryReqVO;
import org.example.controller.project.tag.vo.TagUpdateReqVo;
import org.example.controller.project.tag.vo.TagUpdateStatusReqVo;
import org.example.convert.project.TagConvert;
import org.example.dal.dataobject.project.Tag;
import org.example.service.project.tag.TagService;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.common.pojo.SimpleData;

import java.util.List;

@RestController
@RequestMapping("/project/tag")
public class TagController {
    @Resource
    private TagService service;

    @GetMapping
    public Result<?> getPage(@RequestHeader("x-project-id") Long projectId, TagQueryReqVO req) {
        // 获得标签分页列表
        req.setProjectId(projectId);
        return Result.getSuccessful(TagConvert.INSTANCE.convert(service.getPage(req)));
    }

    @PostMapping
    public Result<?> add(@RequestHeader("x-project-id") Long projectId, @RequestBody TagAddReqVO data) {
        data.setProjectId(projectId);
        service.add(TagConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @PutMapping
    public Result<?> update(@RequestHeader("x-project-id") Long projectId, @RequestBody TagUpdateReqVo data) {
        data.setProjectId(projectId);
        service.update(TagConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @PutMapping("/status")
    public Result<?> updateStatus(@RequestHeader("x-project-id") Long projectId, @RequestBody TagUpdateStatusReqVo data) {
        data.setProjectId(projectId);
        service.update(TagConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable Long id) {
        service.remove(id);
        return Result.getSuccessful();
    }

    @GetMapping("/simple")
    public Result<List<SimpleData<Long>>> getSimple(@RequestHeader("x-project-id") Long projectId) {
        // 获得用户列表，只要开启状态的
        List<Tag> list = service.getList(projectId);
        return Result.getSuccessful(TagConvert.INSTANCE.convert1(list));
    }
}
