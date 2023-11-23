package org.example.convert.project;

import com.google.common.collect.Lists;
import org.example.controller.project.vo.ProjectAddReqVO;
import org.example.controller.project.vo.ProjectRespVO;
import org.example.controller.project.vo.ProjectUpdateReqVO;
import org.example.dal.dataobject.project.Project;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.SimpleData;

import java.util.List;
import java.util.Objects;

import static xyz.migoo.framework.common.enums.CommonStatusEnum.ENABLE;

@Mapper
public interface ProjectConvert {

    ProjectConvert INSTANCE = Mappers.getMapper(ProjectConvert.class);

    Project convert(ProjectAddReqVO bean);

    Project convert(ProjectUpdateReqVO bean);

    ProjectRespVO convert(Project bean);

    PageResult<ProjectRespVO> convert(PageResult<Project> beans);

    default List<SimpleData<Long>> convert(List<Project> projects) {
        List<SimpleData<Long>> result = Lists.newArrayList();

        projects.forEach(item -> result.add(convert1(item)));
        return result;
    }

    default SimpleData<Long> convert1(Project project) {
        return new SimpleData<>(project.getId(), project.getName(),
                !Objects.equals(project.getStatus(), ENABLE.getStatus()));
    }
}
