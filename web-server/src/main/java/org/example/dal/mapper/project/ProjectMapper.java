package org.example.dal.mapper.project;

import org.apache.ibatis.annotations.Mapper;
import org.example.controller.project.vo.ProjectQueryReqVO;
import org.example.dal.dataobject.project.Project;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

@Mapper
public interface ProjectMapper extends BaseMapperX<Project> {

    default PageResult<Project> selectPage(ProjectQueryReqVO req) {
        return selectPage(req, new LambdaQueryWrapperX<Project>()
                .likeIfPresent(Project::getName, req.getName())
                .eqIfPresent(Project::getStatus, req.getStatus())
                .orderByDesc(Project::getId));
    }

}
