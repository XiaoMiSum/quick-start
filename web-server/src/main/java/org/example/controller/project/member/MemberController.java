package org.example.controller.project.member;

import jakarta.annotation.Resource;
import org.example.controller.project.member.vo.MemberAddReqVO;
import org.example.controller.project.member.vo.MemberQueryReqVO;
import org.example.convert.project.MemberConvert;
import org.example.service.project.member.MemberService;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.Result;

@RestController
@RequestMapping("/project/member")
public class MemberController {

    @Resource
    private MemberService service;

    @GetMapping
    public Result<?> getPage(@RequestHeader("x-project-id") Long projectId, MemberQueryReqVO req) {
        // 获得项目成员分页列表
        req.setProjectId(projectId);
        return Result.getSuccessful(service.getPage(req));
    }

    @PostMapping
    public Result<?> add(@RequestHeader("x-project-id") Long projectId, @RequestBody MemberAddReqVO data) {
        data.setProjectId(projectId);
        service.add(MemberConvert.INSTANCE.convert(data));
        return Result.getSuccessful();
    }

    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable Long id) {
        service.remove(id);
        return Result.getSuccessful();
    }
}
