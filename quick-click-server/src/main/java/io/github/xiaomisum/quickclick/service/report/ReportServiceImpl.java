package io.github.xiaomisum.quickclick.service.report;

import io.github.xiaomisum.quickclick.controller.report.vo.ReportQueryReqVO;
import io.github.xiaomisum.quickclick.controller.report.vo.days.DeveloperDaysPageRespVO;
import io.github.xiaomisum.quickclick.dal.dataobject.report.ReportBasicData;
import io.github.xiaomisum.quickclick.dal.mapper.report.ReportBasicDataMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

@Service
public class ReportServiceImpl implements ReportService {


    @Resource
    private ReportBasicDataMapper basicDataMapper;

    @Override
    public PageResult<DeveloperDaysPageRespVO> getDevDays(ReportQueryReqVO req) {
        return basicDataMapper.selectPage(req);
    }

    @Override
    public void updateDevDays(ReportBasicData data) {
        basicDataMapper.updateById(data);
    }
}
