package io.github.xiaomisum.quickclick.dal.mapper.report;

import io.github.xiaomisum.quickclick.dal.dataobject.report.ProjectMonthData;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

import java.util.List;

@Mapper
public interface ProjectMonthDataMapper extends BaseMapperX<ProjectMonthData> {

    default List<ProjectMonthData> selectListByProjectId(String projectId) {
        return selectList(new LambdaQueryWrapperX<ProjectMonthData>()
                .eq(ProjectMonthData::getProjectId, projectId));
    }
}