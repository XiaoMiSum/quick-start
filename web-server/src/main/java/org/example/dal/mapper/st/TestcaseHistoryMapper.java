package org.example.dal.mapper.st;

import org.apache.ibatis.annotations.Mapper;
import org.example.dal.dataobject.st.TestcaseHistory;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

import java.util.List;

@Mapper
public interface TestcaseHistoryMapper extends BaseMapperX<TestcaseHistory> {

    PageResult<TestcaseHistory> selectPage(Long projectId, String version);

    default List<TestcaseHistory> selectList(Long projectId, String version) {
        return selectList(new LambdaQueryWrapperX<TestcaseHistory>()
                .eq(TestcaseHistory::getProjectId, projectId)
                .eq(TestcaseHistory::getVersion, version)
        );
    }

    default List<TestcaseHistory> selectList(Long projectId) {
        return selectList(new LambdaQueryWrapperX<TestcaseHistory>()
                .eq(TestcaseHistory::getProjectId, projectId)
        );
    }

    default void deleteByVersion(Long projectId, String version) {
        delete(new LambdaQueryWrapperX<TestcaseHistory>()
                .eq(TestcaseHistory::getProjectId, projectId)
                .eq(TestcaseHistory::getVersion, version)
        );
    }
}
