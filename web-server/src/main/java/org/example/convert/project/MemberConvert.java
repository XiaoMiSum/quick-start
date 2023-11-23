package org.example.convert.project;

import com.google.common.collect.Lists;
import org.example.controller.project.member.vo.MemberAddReqVO;
import org.example.dal.dataobject.project.ProjectMember;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MemberConvert {

    MemberConvert INSTANCE = Mappers.getMapper(MemberConvert.class);

    default List<ProjectMember> convert(MemberAddReqVO data) {
        List<ProjectMember> list = Lists.newArrayList();
        data.getUserIds().forEach(item -> list.add(new ProjectMember().setProjectId(data.getProjectId()).setUserId(item)));
        return list;
    }
}
