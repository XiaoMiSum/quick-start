package org.example.dal.mapper.project;

import org.apache.ibatis.annotations.Mapper;
import org.example.controller.project.env.vo.EnvQueryReqVO;
import org.example.dal.dataobject.project.ProjectEnv;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

import java.util.List;

@Mapper
public interface ProjectEnvMapper extends BaseMapperX<ProjectEnv> {

    default List<ProjectEnv> selectList(Long projectId) {
        return selectList(new LambdaQueryWrapperX<ProjectEnv>()
                .eq(ProjectEnv::getProjectId, projectId)
                .orderByDesc(ProjectEnv::getId)
        );
    }


    default PageResult<ProjectEnv> selectPage(EnvQueryReqVO req) {
        return selectPage(req, new LambdaQueryWrapperX<ProjectEnv>()
                .eq(ProjectEnv::getProjectId, req.getProjectId())
                .orderByDesc(ProjectEnv::getId)
        );
    }

    default ProjectEnv selectOne(Long projectId, Long id) {
        return selectOne(new LambdaQueryWrapperX<ProjectEnv>()
                .eq(ProjectEnv::getId, id)
                .eq(ProjectEnv::getProjectId, projectId)
        );
    }

}
