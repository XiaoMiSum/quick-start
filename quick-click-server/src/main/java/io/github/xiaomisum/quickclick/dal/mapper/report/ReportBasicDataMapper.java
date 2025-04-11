package io.github.xiaomisum.quickclick.dal.mapper.report;

import io.github.xiaomisum.quickclick.controller.report.vo.ReportQueryReqVO;
import io.github.xiaomisum.quickclick.controller.report.vo.days.DeveloperDaysPageRespVO;
import io.github.xiaomisum.quickclick.dal.dataobject.report.ReportBasicData;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.infra.dal.dataobject.sys.User;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.MPJLambdaWrapperX;

@Mapper
public interface ReportBasicDataMapper extends BaseMapperX<ReportBasicData> {

    default PageResult<DeveloperDaysPageRespVO> selectPage(ReportQueryReqVO req) {
        return selectJoinPage(req, DeveloperDaysPageRespVO.class, new MPJLambdaWrapperX<ReportBasicData>()
                .selectAs(User::getName, "name")
                .selectAll(ReportBasicData.class)
                .leftJoin(User.class, on -> on.eq(ReportBasicData::getUserId, User::getId))
                .eq(ReportBasicData::getProjectId, req.getProjectId())
                .eqIfPresent(ReportBasicData::getDate, req.getDate())
                .eqIfPresent(ReportBasicData::getUserId, req.getUserId())
                .orderByDesc(ReportBasicData::getDate));
    }
}
