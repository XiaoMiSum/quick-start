package org.example.convert.project;

import org.example.controller.project.env.vo.EnvAddReqVO;
import org.example.controller.project.env.vo.EnvRespVO;
import org.example.controller.project.env.vo.EnvUpdateReqVO;
import org.example.dal.dataobject.project.ProjectEnv;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Mapper
public interface EnvConvert {

    EnvConvert INSTANCE = Mappers.getMapper(EnvConvert.class);

    ProjectEnv convert(EnvAddReqVO bean);

    ProjectEnv convert(EnvUpdateReqVO bean);

    EnvRespVO convert(ProjectEnv bean);

    PageResult<EnvRespVO> convert(PageResult<ProjectEnv> beans);

    List<EnvRespVO> convert(List<ProjectEnv> beans);

}
