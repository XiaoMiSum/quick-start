package org.example.dal.mapper.project;

import org.apache.ibatis.annotations.Mapper;
import org.example.controller.project.member.vo.MemberQueryReqVO;
import org.example.controller.project.member.vo.MemberRespVO;
import org.example.dal.dataobject.project.ProjectMember;
import org.example.dal.dataobject.sys.User;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;
import xyz.migoo.framework.mybatis.core.MPJLambdaWrapperX;

import java.util.List;

@Mapper
public interface ProjectMemberMapper extends BaseMapperX<ProjectMember> {

    default PageResult<MemberRespVO> selectPage(MemberQueryReqVO req) {
        return selectJoinPage(req, MemberRespVO.class, new MPJLambdaWrapperX<ProjectMember>()
                .selectAll(ProjectMember.class)
                .selectAs(User::getName, MemberRespVO::getMemberName)
                .leftJoinX(User.class, on -> on.eq(ProjectMember::getUserId, User::getId))
                .eq(ProjectMember::getProjectId, req.getProjectId())
                .orderByDesc(ProjectMember::getId)
        );
    }

    default List<ProjectMember> selectList(Long projectId) {
        return selectList(new LambdaQueryWrapperX<ProjectMember>()
                .eq(ProjectMember::getProjectId, projectId)
                .orderByDesc(ProjectMember::getId)
        );
    }

    default ProjectMember selectOne(Long projectId, Long userId) {
        return selectOne("project_id", projectId, "user_id", userId);
    }
}
