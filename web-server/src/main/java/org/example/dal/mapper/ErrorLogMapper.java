package org.example.dal.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.dal.dataobject.ApiErrorLogDO;
import xyz.migoo.framework.mybatis.core.BaseMapperX;

@Mapper
public interface ErrorLogMapper extends BaseMapperX<ApiErrorLogDO> {

}
