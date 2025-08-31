package io.github.xiaomisum.quickclick.dal.mapper.report;

import io.github.xiaomisum.quickclick.dal.dataobject.report.ProjectWeekData;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

import java.util.List;

@Mapper
public interface ProjectWeekDataMapper extends BaseMapperX<ProjectWeekData> {

    default List<ProjectWeekData> selectListByProjectId(String projectId) {
        return selectList(new LambdaQueryWrapperX<ProjectWeekData>()
                .eq(ProjectWeekData::getProjectId, projectId));
    }
}