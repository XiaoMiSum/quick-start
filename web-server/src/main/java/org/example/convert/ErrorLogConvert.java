package org.example.convert;

import org.example.dal.dataobject.ApiErrorLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.apilog.core.ApiErrorLog;

@Mapper
public interface ErrorLogConvert {

    ErrorLogConvert INSTANCE = Mappers.getMapper(ErrorLogConvert.class);

    ApiErrorLogDO convert(ApiErrorLog bean);
}
