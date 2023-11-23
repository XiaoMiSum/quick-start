package org.example.service;

import jakarta.annotation.Resource;
import org.example.convert.ErrorLogConvert;
import org.example.dal.mapper.ErrorLogMapper;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.apilog.core.ApiErrorLog;
import xyz.migoo.framework.apilog.core.ApiErrorLogFrameworkService;

@Service
public class ErrorLogServiceImpl implements ApiErrorLogFrameworkService {
    @Resource
    private ErrorLogMapper mapper;

    @Override
    public void createApiErrorLog(ApiErrorLog apiErrorLog) {
        mapper.insert(ErrorLogConvert.INSTANCE.convert(apiErrorLog));
    }
}