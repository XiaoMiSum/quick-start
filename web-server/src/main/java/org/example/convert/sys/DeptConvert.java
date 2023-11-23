package org.example.convert.sys;

import org.example.controller.sys.dept.vo.DeptAddReqVO;
import org.example.controller.sys.dept.vo.DeptRespVO;
import org.example.controller.sys.dept.vo.DeptSimpleRespVO;
import org.example.controller.sys.dept.vo.DeptUpdateReqVO;
import org.example.dal.dataobject.sys.Dept;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DeptConvert {

    DeptConvert INSTANCE = Mappers.getMapper(DeptConvert.class);

    Dept convert(DeptAddReqVO updateReq);

    Dept convert(DeptUpdateReqVO updateReq);

    DeptRespVO convert(Dept list);

    List<DeptRespVO> convert(List<Dept> list);

    List<DeptSimpleRespVO> convert0(List<Dept> list);
}
