package org.example.convert.project;

import org.example.controller.project.link.vo.LinkAddReqVO;
import org.example.controller.project.link.vo.LinkRespVO;
import org.example.controller.project.link.vo.LinkUpdateReqVO;
import org.example.dal.dataobject.project.ProjectLink;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Mapper
public interface LinkConvert {

    LinkConvert INSTANCE = Mappers.getMapper(LinkConvert.class);

    ProjectLink convert(LinkAddReqVO bean);

    ProjectLink convert(LinkUpdateReqVO bean);

    LinkRespVO convert(ProjectLink bean);

    PageResult<LinkRespVO> convert(PageResult<ProjectLink> beans);

    List<LinkRespVO> convert(List<ProjectLink> beans);

}
