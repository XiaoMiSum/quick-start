/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021.  Lorem XiaoMiSum (mi_xiao@qq.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * 'Software'), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.github.xiaomisum.quickclick.dal.mapper.sys;

import io.github.xiaomisum.quickclick.controller.sys.permission.role.vo.RoleQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.sys.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.Nullable;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapperX<Role> {

    default Role selectByName(String name) {
        return selectOne(new LambdaQueryWrapperX<Role>().eq(Role::getName, name));
    }

    default Role selectByCode(String code) {
        return selectOne(new LambdaQueryWrapperX<Role>().eq(Role::getCode, code));
    }

    default List<Role> selectListByStatus(@Nullable Collection<Integer> statuses) {
        return selectList(new LambdaQueryWrapperX<Role>().in(Role::getStatus, statuses));
    }

    default boolean selectExistsByUpdateTimeAfter(Date maxUpdateTime) {
        return selectOne(new LambdaQueryWrapperX<Role>().select(Role::getId)
                .gt(BaseDO::getUpdateTime, maxUpdateTime).last("LIMIT 1")) != null;
    }

    default PageResult<Role> selectPage(RoleQueryReqVO req) {
        return selectPage(req, new LambdaQueryWrapperX<Role>()
                .likeIfPresent(Role::getName, req.getName())
                .likeIfPresent(Role::getCode, req.getCode())
                .eqIfPresent(Role::getStatus, req.getStatus())
                .gt(Role::getId, 1)
                .orderByAsc(Role::getId));
    }

}
