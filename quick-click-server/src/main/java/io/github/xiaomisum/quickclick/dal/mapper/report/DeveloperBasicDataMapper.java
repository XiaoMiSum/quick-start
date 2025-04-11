package io.github.xiaomisum.quickclick.dal.mapper.report;

import io.github.xiaomisum.quickclick.controller.report.vo.ReportQueryReqVO;
import io.github.xiaomisum.quickclick.controller.report.vo.days.DeveloperDaysPageRespVO;
import io.github.xiaomisum.quickclick.dal.dataobject.report.DeveloperBasicData;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.infra.dal.dataobject.sys.User;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.MPJLambdaWrapperX;

@Mapper
public interface DeveloperBasicDataMapper extends BaseMapperX<DeveloperBasicData> {

    default PageResult<DeveloperDaysPageRespVO> selectPage(ReportQueryReqVO req) {
        return selectJoinPage(req, DeveloperDaysPageRespVO.class, new MPJLambdaWrapperX<DeveloperBasicData>()
                .selectAs(User::getName, "name")
                .selectAll(DeveloperBasicData.class)
                .leftJoin(User.class, on -> on.eq(DeveloperBasicData::getUserId, User::getId))
                .eq(DeveloperBasicData::getProjectId, req.getProjectId())
                .eqIfPresent(DeveloperBasicData::getDate, req.getDate())
                .eqIfPresent(DeveloperBasicData::getUserId, req.getUserId())
                .orderByDesc(DeveloperBasicData::getDate));
    }
}
