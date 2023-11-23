package org.example.dal.mapper.sys;

import org.apache.ibatis.annotations.Mapper;
import org.example.controller.sys.user.vo.UserQueryReqVO;
import org.example.dal.dataobject.sys.User;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapperX<User> {


    default User selectByPhone(String phone) {
        return selectOne(new LambdaQueryWrapperX<User>()
                .eq(User::getPhone, phone));
    }

    default PageResult<User> selectPage(UserQueryReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<User>()
                .likeIfPresent(User::getPhone, reqVO.getPhone())
                .likeIfPresent(User::getName, reqVO.getName())
                .eqIfPresent(User::getDeptId, reqVO.getDeptId())
                .eqIfPresent(User::getStatus, reqVO.getStatus())
                .gt(User::getId, 1)
                .orderByDesc(User::getId));
    }

    default List<User> selectList(Integer status) {
        return selectList(new LambdaQueryWrapperX<User>()
                .eqIfPresent(User::getStatus, status)
                .orderByDesc(User::getId));
    }
}
