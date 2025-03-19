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

import io.github.xiaomisum.quickclick.controller.quality.testcase.vo.TestcaseQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Testcase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

import java.util.List;

@Mapper
public interface TestcaseMapper extends BaseMapperX<Testcase> {

    @Select("""
            <script>
            select
            id, project_id projectId, node_id nodeId, title, priority, tags, supervisor,
            last_review_result lastReviewResult, last_review_time lastReviewTime,
            creator, create_time createTime, updater, update_time updateTime
            from qc_quality_testcase
            where deleted = #{req.deleted} and project_id = #{req.projectId}
            <if test="req.nodeId != null and req.nodeId != ''">
                and node_id = #{req.nodeId}
            </if>
            <if test="req.title != null and req.title != ''">
                and title like concat('%', #{req.title}, '%')
            </if>
            <if test="req.priority != null and req.priority != ''">
                and priority = #{req.priority}
            </if>
            <if test="req.supervisor != null">
                and supervisor = #{req.supervisor}
            </if>
            
            </script>
            """)
    PageResult<Testcase> selectPage(TestcaseQueryReqVO req);


    default List<Testcase> selectList(String projectId) {
        return selectList(new LambdaQueryWrapperX<Testcase>().eq(Testcase::getProjectId, projectId));
    }


    default PageResult<Testcase> selectPage(TestcaseQueryReqVO req, List<String> notInIds) {
        return selectPage(req, new LambdaQueryWrapperX<Testcase>()
                .eq(Testcase::getProjectId, req.getProjectId())
                .eqIfPresent(Testcase::getNodeId, req.getNodeId())
                .likeIfPresent(Testcase::getTitle, req.getTitle())
                .eqIfPresent(Testcase::getPriority, req.getPriority())
                .notInIfPresent(Testcase::getId, notInIds)
        );
    }

    @Update("""
            <script>
            delete from qc_quality_testcase where deleted = 1 and project_id = #{projectId}
            <if test="ids != null and and ids.size() > 0">
            and id in
            <foreach collection="ids" item="id" index="index" open="(" close=")" separator=",">
                    #{id}
            </foreach>
            </if>
            </script>
            """)
    void clear(@Param("ids") List<String> ids, @Param("projectId") String projectId);

    @Update("""
            <script>
            update qc_quality_testcase set deleted = 0 where deleted = 1 and project_id = #{projectId}
            <if test="ids != null and and ids.size() > 0">
            and id in
            <foreach collection="ids" item="id" index="index" open="(" close=")" separator=",">
                    #{id}
            </foreach>
            </if>
            </script>
            """)
    void recover(@Param("ids") List<String> ids, @Param("projectId") String projectId);

}
