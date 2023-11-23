package org.example.service.project.member;

import jakarta.annotation.Resource;
import org.example.controller.project.member.vo.MemberQueryReqVO;
import org.example.controller.project.member.vo.MemberRespVO;
import org.example.dal.dataobject.project.ProjectMember;
import org.example.dal.mapper.project.ProjectMemberMapper;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;
import java.util.Objects;

@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    private ProjectMemberMapper mapper;

    @Override
    public PageResult<MemberRespVO> getPage(MemberQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public List<ProjectMember> getList(Long projectId) {
        return mapper.selectList(projectId);
    }

    @Override
    public void add(List<ProjectMember> members) {
        members.forEach(item -> {
            if (Objects.isNull(mapper.selectOne(item.getProjectId(), item.getUserId()))) {
                mapper.insert(item);
            }
        });
    }

    @Override
    public void remove(Long id) {
        mapper.deleteById(id);
    }
}
