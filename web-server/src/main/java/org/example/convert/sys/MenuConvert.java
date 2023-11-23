package org.example.convert.sys;

import org.example.controller.sys.permission.menu.vo.MenuAddReqVO;
import org.example.controller.sys.permission.menu.vo.MenuRespVO;
import org.example.controller.sys.permission.menu.vo.MenuSimpleRespVO;
import org.example.controller.sys.permission.menu.vo.MenuUpdateReqVO;
import org.example.dal.dataobject.sys.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MenuConvert {
    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    List<MenuRespVO> convert(List<Menu> list);

    List<MenuSimpleRespVO> convert0(List<Menu> list);

    MenuRespVO convert(Menu menu);

    Menu convert(MenuAddReqVO addReq);

    Menu convert(MenuUpdateReqVO updateReq);
}
