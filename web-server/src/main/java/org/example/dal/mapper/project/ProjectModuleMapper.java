package org.example.dal.mapper.project;

import org.apache.ibatis.annotations.Mapper;
import org.example.dal.dataobject.project.ProjectModule;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

import java.util.List;

@Mapper
public interface ProjectModuleMapper extends BaseMapperX<ProjectModule> {

    default List<ProjectModule> selectList(Long projectId) {
        return selectList(new LambdaQueryWrapperX<ProjectModule>()
                .eq(ProjectModule::getProjectId, projectId)
        );
    }

    default ProjectModule select(Long projectId, Long id) {
        return selectOne(new LambdaQueryWrapperX<ProjectModule>()
                .eq(ProjectModule::getProjectId, projectId).eq(ProjectModule::getId, id)
        );
    }
}
