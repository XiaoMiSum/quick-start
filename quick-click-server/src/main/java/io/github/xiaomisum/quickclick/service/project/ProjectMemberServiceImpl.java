package io.github.xiaomisum.quickclick.service.project;

import io.github.xiaomisum.quickclick.controller.project.member.vo.MemberPageReqVO;
import io.github.xiaomisum.quickclick.controller.project.member.vo.MemberPageRespVO;
import io.github.xiaomisum.quickclick.dal.dataobject.project.ProjectMember;
import io.github.xiaomisum.quickclick.dal.mapper.project.MemberJoinMapper;
import io.github.xiaomisum.quickclick.dal.mapper.project.MemberMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

    @Resource
    private MemberMapper mapper;
    @Resource
    private MemberJoinMapper joinMapper;

    @Override
    public List<String> getProjectIds(Long userId) {
        return mapper.selectListByUserId(userId).stream().map(ProjectMember::getProjectId).toList();
    }

    @Override
    public List<MemberPageRespVO> getList(String projectId) {
        return joinMapper.selectList(projectId);
    }

    @Override
    public PageResult<MemberPageRespVO> getPage(MemberPageReqVO req) {
        return joinMapper.selectPage(req);
    }

    @Override
    public void add(ProjectMember data) {
        mapper.insert(data);
    }

    @Override
    public void update(ProjectMember data) {
        mapper.updateById(data);
    }

    @Override
    public void remove(List<String> ids) {
        mapper.deleteByIds(ids);
    }

}
