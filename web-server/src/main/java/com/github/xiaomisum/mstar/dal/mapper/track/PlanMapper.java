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

package com.github.xiaomisum.mstar.dal.mapper.track;

import com.github.xiaomisum.mstar.controller.track.plan.vo.PlanQueryReqVO;
import com.github.xiaomisum.mstar.dal.dataobject.track.Plan;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

import java.util.List;

@Mapper
public interface PlanMapper extends BaseMapperX<Plan> {

    default PageResult<Plan> selectPage(PlanQueryReqVO req) {
        return selectPage(req, new LambdaQueryWrapperX<Plan>()
                .eq(Plan::getProjectId, req.getProjectId())
                .likeIfPresent(Plan::getName, req.getName())
        );
    }

    default List<Plan> selectList(String projectId) {
        return selectList(new LambdaQueryWrapperX<Plan>().eq(Plan::getProjectId, projectId));
    }

    default Plan selectOne(String projectId, String id) {
        return selectOne(new LambdaQueryWrapperX<Plan>()
                .eq(Plan::getId, id)
                .eq(Plan::getProjectId, projectId));
    }
}
