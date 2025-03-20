package io.github.xiaomisum.quickclick.service.project;

import io.github.xiaomisum.quickclick.controller.project.member.vo.MemberPageReqVO;
import io.github.xiaomisum.quickclick.controller.project.member.vo.MemberPageRespVO;
import io.github.xiaomisum.quickclick.dal.dataobject.project.ProjectMember;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface ProjectMemberService {


    List<String> getProjectIds(Long userId);

    List<MemberPageRespVO> getList(String projectId);

    PageResult<MemberPageRespVO> getPage(MemberPageReqVO req);

    void add(ProjectMember data);

    void update(ProjectMember data);

    void remove(List<Long> ids);


    void add(String projectId, Long userId);
}
