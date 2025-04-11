package io.github.xiaomisum.quickclick.service.report;

import io.github.xiaomisum.quickclick.controller.report.vo.ReportQueryReqVO;
import io.github.xiaomisum.quickclick.controller.report.vo.days.DeveloperDaysPageRespVO;
import io.github.xiaomisum.quickclick.dal.dataobject.report.DeveloperBasicData;
import io.github.xiaomisum.quickclick.dal.mapper.report.DeveloperBasicDataMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

@Service
public class ReportServiceImpl implements ReportService {


    @Resource
    private DeveloperBasicDataMapper basicDataMapper;

    @Override
    public PageResult<DeveloperDaysPageRespVO> getDevDays(ReportQueryReqVO req) {
        return basicDataMapper.selectPage(req);
    }

    @Override
    public void updateDevDays(DeveloperBasicData data) {
        basicDataMapper.updateById(data);
    }
}
