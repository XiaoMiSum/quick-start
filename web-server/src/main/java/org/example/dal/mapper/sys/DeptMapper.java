package org.example.dal.mapper.sys;

import org.apache.ibatis.annotations.Mapper;
import org.example.controller.sys.dept.vo.DeptQueryReqVO;
import org.example.dal.dataobject.sys.Dept;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

import java.util.Date;
import java.util.List;

@Mapper
public interface DeptMapper extends BaseMapperX<Dept> {

    default List<Dept> selectList(DeptQueryReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<Dept>()
                .likeIfPresent(Dept::getName, reqVO.getName())
                .eqIfPresent(Dept::getStatus, reqVO.getStatus()));
    }

    default Dept selectByParentIdAndName(Long parentId, String name) {
        return selectOne(new LambdaQueryWrapperX<Dept>()
                .eq(Dept::getParentId, parentId)
                .eq(Dept::getName, name));
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(new LambdaQueryWrapperX<Dept>().eq(Dept::getParentId, parentId));
    }

    default boolean selectExistsByUpdateTimeAfter(Date maxUpdateTime) {
        return selectOne(new LambdaQueryWrapperX<Dept>().select(Dept::getId)
                .gt(Dept::getUpdateTime, maxUpdateTime).last("LIMIT 1")) != null;
    }
}
