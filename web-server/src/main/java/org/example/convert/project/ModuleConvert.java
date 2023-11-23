package org.example.convert.project;

import org.example.controller.project.module.vo.ModuleAddReqVO;
import org.example.controller.project.module.vo.ModuleRespVO;
import org.example.controller.project.module.vo.ModuleUpdateReqVO;
import org.example.controller.sys.dept.vo.DeptSimpleRespVO;
import org.example.dal.dataobject.project.ProjectModule;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Mapper
public interface ModuleConvert {

    ModuleConvert INSTANCE = Mappers.getMapper(ModuleConvert.class);

    ModuleRespVO convert(ProjectModule bean);

    ProjectModule convert(ModuleAddReqVO bean);

    ProjectModule convert(ModuleUpdateReqVO bean);

    PageResult<ModuleRespVO> convert(PageResult<ProjectModule> beans);

    List<ModuleRespVO> convert(List<ProjectModule> beans);

    List<DeptSimpleRespVO> convert1(List<ProjectModule> beans);
}