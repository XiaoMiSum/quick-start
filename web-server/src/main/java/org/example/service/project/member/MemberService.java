package org.example.service.project.member;

import org.example.controller.project.member.vo.MemberQueryReqVO;
import org.example.controller.project.member.vo.MemberRespVO;
import org.example.dal.dataobject.project.ProjectMember;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface MemberService {


    PageResult<MemberRespVO> getPage(MemberQueryReqVO req);

    List<ProjectMember> getList(Long projectId);

    void add(List<ProjectMember> members);

    void remove(Long id);
}
