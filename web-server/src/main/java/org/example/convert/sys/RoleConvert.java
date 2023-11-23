package org.example.convert.sys;

import org.example.controller.sys.permission.role.vo.*;
import org.example.dal.dataobject.sys.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Mapper
public interface RoleConvert {

    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);

    Role convert(RoleAddReqVO reqVO);

    Role convert(RoleUpdateReqVO reqVO);

    RoleRespVO convert(Role role);

    List<RoleSimpleRespVO> convert(List<Role> list);

    PageResult<RoleRespVO> convert(PageResult<Role> page);

    Role convert(RoleStatusUpdateReqVO reqVO);
}
