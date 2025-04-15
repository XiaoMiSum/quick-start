package io.github.xiaomisum.quickclick.dal.mapper.report;

import io.github.xiaomisum.quickclick.controller.project.days.vo.DaysQueryReqVO;
import io.github.xiaomisum.quickclick.controller.project.days.vo.TesterDaysPageRespVO;
import io.github.xiaomisum.quickclick.dal.dataobject.report.TesterBasicData;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.infra.dal.dataobject.sys.User;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.MPJLambdaWrapperX;

@Mapper
public interface TesterBasicDataMapper extends BaseMapperX<TesterBasicData> {

    default PageResult<TesterDaysPageRespVO> selectPage(DaysQueryReqVO req) {
        return selectJoinPage(req, TesterDaysPageRespVO.class, new MPJLambdaWrapperX<TesterBasicData>()
                .selectAs(User::getName, "name")
                .selectAll(TesterBasicData.class)
                .leftJoin(User.class, on -> on.eq(TesterBasicData::getUserId, User::getId))
                .eq(TesterBasicData::getProjectId, req.getProjectId())
                .eqIfPresent(TesterBasicData::getDate, req.getDate())
                .eqIfPresent(TesterBasicData::getUserId, req.getUserId())
                .orderByDesc(TesterBasicData::getDate));
    }
}
