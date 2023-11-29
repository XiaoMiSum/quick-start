package org.example.dal.mapper.tracked;

import org.apache.ibatis.annotations.Mapper;
import org.example.controller.tracked.testcase.vo.TestcaseQueryReqVO;
import org.example.dal.dataobject.tracked.Testcase;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

import java.util.List;

@Mapper
public interface TestcaseMapper extends BaseMapperX<Testcase> {

    default PageResult<Testcase> selectPage(TestcaseQueryReqVO req) {
        return selectPage(req, new LambdaQueryWrapperX<Testcase>()
                .eq(Testcase::getProjectId, req.getProjectId())
                .eqIfPresent(Testcase::getLevel, req.getLevel())
                .eqIfPresent(Testcase::getModuleId, req.getModuleId())
                .likeIfPresent(Testcase::getName, req.getName())
        );
    }

    default List<Testcase> selectList(Long projectId) {
        return selectList(new LambdaQueryWrapperX<Testcase>().eq(Testcase::getProjectId, projectId));
    }


    default PageResult<Testcase> selectPage(TestcaseQueryReqVO req, List<Long> notInIds) {
        return selectPage(req, new LambdaQueryWrapperX<Testcase>()
                .eq(Testcase::getProjectId, req.getProjectId())
                .eqIfPresent(Testcase::getModuleId, req.getModuleId())
                .likeIfPresent(Testcase::getName, req.getName())
                .eqIfPresent(Testcase::getLevel, req.getLevel())
                .notInIfPresent(Testcase::getId, notInIds)
        );
    }
}
