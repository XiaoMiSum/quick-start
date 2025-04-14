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

package io.github.xiaomisum.quickclick.dal.mapper.qualitycenter;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import io.github.xiaomisum.quickclick.controller.quality.testcase.vo.TestcaseQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Testcase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TestcaseMapper extends BaseMapperX<Testcase> {


    default PageResult<Testcase> selectPage(TestcaseQueryReqVO req) {
        return selectPage(req, new LambdaQueryWrapperX<Testcase>()
                .eq(Testcase::getProjectId, req.getProjectId())
                .eq(Testcase::getTrash, req.getTrash())
                .eqIfPresent(Testcase::getNodeId, req.getNodeId())
                .likeIfPresent(Testcase::getTitle, req.getTitle())
                .eqIfPresent(Testcase::getPriority, req.getPriority())
                .eqIfPresent(Testcase::getSupervisor, req.getSupervisor()));
    }

    default List<Testcase> selectList(String projectId) {
        return selectList(new LambdaQueryWrapperX<Testcase>()
                .eq(Testcase::getProjectId, projectId)
                .eq(Testcase::getTrash, 0));
    }

    default PageResult<Testcase> selectPage(TestcaseQueryReqVO req, List<String> notInIds) {
        return selectPage(req, new LambdaQueryWrapperX<Testcase>()
                .eq(Testcase::getTrash, 0)
                .eq(Testcase::getProjectId, req.getProjectId())
                .eqIfPresent(Testcase::getNodeId, req.getNodeId())
                .likeIfPresent(Testcase::getTitle, req.getTitle())
                .eqIfPresent(Testcase::getPriority, req.getPriority())
                .notInIfPresent(Testcase::getId, notInIds)
        );
    }

    @Update("""
            <script>
            delete from qc_quality_testcase where trash = 1 and project_id = #{projectId}
            <if test="ids != null and ids.size() > 0">
            and id in
            <foreach collection="ids" item="id" index="index" open="(" close=")" separator=",">
                    #{id}
            </foreach>
            </if>
            </script>
            """)
    void clear(@Param("ids") List<String> ids, @Param("projectId") String projectId);

    default void recover(List<String> ids, String projectId) {
        update(new LambdaUpdateWrapper<Testcase>()
                .set(Testcase::getTrash, 0)
                .eq(Testcase::getProjectId, projectId)
                .in(Testcase::getId, ids));
    }

    default void moveToTrash(List<String> ids, String projectId) {
        update(new LambdaUpdateWrapper<Testcase>()
                .set(Testcase::getTrash, 1)
                .eq(Testcase::getProjectId, projectId)
                .in(Testcase::getId, ids));
    }

    default List<Testcase> selectByUpdateTimeAfter(LocalDateTime maxUpdateTime) {
        return selectList(new LambdaQueryWrapperX<Testcase>()
                .ge(Testcase::getUpdateTime, maxUpdateTime)
                .eq(Testcase::getTrash, 0));
    }

    default List<Testcase> selectList(LocalDateTime startTime, LocalDateTime endTime) {
        return selectList(new LambdaQueryWrapperX<Testcase>()
                .eq(Testcase::getTrash, 0)
                .ge(Testcase::getCreateTime, startTime)
                .le(BaseDO::getCreateTime, endTime));
    }
}
