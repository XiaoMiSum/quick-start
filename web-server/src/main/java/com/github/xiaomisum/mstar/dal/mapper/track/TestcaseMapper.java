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

import com.github.xiaomisum.mstar.controller.track.testcase.vo.testcase.TestcaseQueryReqVO;
import com.github.xiaomisum.mstar.dal.dataobject.track.Testcase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
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
                .eqIfPresent(Testcase::getReviewed, req.getReviewed())
                .eqIfPresent(Testcase::getNodeId, req.getNodeId())
                .likeIfPresent(Testcase::getTags, req.getTag())
                .likeIfPresent(Testcase::getName, req.getName())
        );
    }

    default List<Testcase> selectList(String projectId) {
        return selectList(new LambdaQueryWrapperX<Testcase>().eq(Testcase::getProjectId, projectId));
    }


    default PageResult<Testcase> selectPage(TestcaseQueryReqVO req, List<String> notInIds) {
        return selectPage(req, new LambdaQueryWrapperX<Testcase>()
                .eq(Testcase::getProjectId, req.getProjectId())
                .eqIfPresent(Testcase::getNodeId, req.getNodeId())
                .likeIfPresent(Testcase::getName, req.getName())
                .eqIfPresent(Testcase::getLevel, req.getLevel())
                .notInIfPresent(Testcase::getId, notInIds)
        );
    }

    @Update("""
            <script>
            update tracked_testcase set deleted = 0 where deleted = 1 and id in \s
            <foreach collection="ids" item="id" index="index" open="(" close=")" separator=",">
                    #{id}
            </foreach>
            </script>
            """)
    void recover(@Param("ids") List<String> ids);
}
