package io.github.xiaomisum.quickclick.service.report;

import io.github.xiaomisum.quickclick.controller.report.vo.ReportQueryReqVO;
import io.github.xiaomisum.quickclick.controller.report.vo.days.DeveloperDaysPageRespVO;
import io.github.xiaomisum.quickclick.controller.report.vo.days.TesterDaysPageRespVO;
import io.github.xiaomisum.quickclick.dal.dataobject.report.DeveloperBasicData;
import io.github.xiaomisum.quickclick.dal.dataobject.report.TesterBasicData;
import io.github.xiaomisum.quickclick.dal.mapper.report.DeveloperBasicDataMapper;
import io.github.xiaomisum.quickclick.dal.mapper.report.TesterBasicDataMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

@Service
public class ReportServiceImpl implements ReportService {

    @Resource
    private DeveloperBasicDataMapper developerBasicDataMapper;
    @Resource
    private TesterBasicDataMapper testerBasicDataMapper;

    @Override
    public PageResult<DeveloperDaysPageRespVO> getDevDays(ReportQueryReqVO req) {
        return developerBasicDataMapper.selectPage(req);
    }

    @Override
    public void updateDevDays(DeveloperBasicData data) {
        developerBasicDataMapper.updateById(data);
    }

    @Override
    public PageResult<TesterDaysPageRespVO> getTestDays(ReportQueryReqVO req) {
        return testerBasicDataMapper.selectPage(req);
    }

    @Override
    public void updateTestDays(TesterBasicData data) {
        testerBasicDataMapper.updateById(data);
    }
}
