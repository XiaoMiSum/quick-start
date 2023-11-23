package org.example.dal.mapper.project;

import org.apache.ibatis.annotations.Mapper;
import org.example.controller.project.link.vo.LinkQueryReqVO;
import org.example.dal.dataobject.project.ProjectLink;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

import java.util.List;

@Mapper
public interface ProjectLinkMapper extends BaseMapperX<ProjectLink> {

    default List<ProjectLink> selectList(Long projectId) {
        return selectList(new LambdaQueryWrapperX<ProjectLink>()
                .eq(ProjectLink::getProjectId, projectId)
                .orderByDesc(ProjectLink::getId)
        );
    }

    default PageResult<ProjectLink> selectPage(LinkQueryReqVO req) {
        return selectPage(req, new LambdaQueryWrapperX<ProjectLink>()
                .eq(ProjectLink::getProjectId, req.getProjectId())
                .orderByDesc(ProjectLink::getId)
        );
    }
}
