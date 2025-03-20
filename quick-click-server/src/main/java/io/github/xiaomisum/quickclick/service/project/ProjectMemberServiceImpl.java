package io.github.xiaomisum.quickclick.service.project;

import io.github.xiaomisum.quickclick.controller.project.member.vo.MemberPageReqVO;
import io.github.xiaomisum.quickclick.controller.project.member.vo.MemberPageRespVO;
import io.github.xiaomisum.quickclick.dal.dataobject.project.ProjectMember;
import io.github.xiaomisum.quickclick.dal.mapper.project.MemberMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;
import java.util.Objects;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

    @Resource
    private MemberMapper mapper;

    @Override
    public List<String> getProjectIds(Long userId) {
        return mapper.selectListByUserId(userId).stream().map(ProjectMember::getProjectId).toList();
    }

    @Override
    public List<MemberPageRespVO> getList(String projectId) {
        return mapper.selectList(projectId);
    }

    @Override
    public PageResult<MemberPageRespVO> getPage(MemberPageReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public void add(ProjectMember data) {
        if (Objects.isNull(mapper.selectOne(data.getProjectId(), data.getUserId()))) {
            mapper.insert(data);
        }
    }

    @Override
    public void update(ProjectMember data) {
        mapper.updateById(data);
    }

    @Override
    public void remove(List<Long> ids) {
        mapper.deleteByIds(ids);
    }

    @Override
    public void add(String projectId, Long userId) {
        this.add(new ProjectMember().setProjectId(projectId).setUserId(userId));
    }

}
