/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2025.  Lorem XiaoMiSum (mi_xiao@qq.com)
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

package io.github.xiaomisum.quickclick.dal.mapper.project;

import io.github.xiaomisum.quickclick.controller.project.member.vo.MemberPageReqVO;
import io.github.xiaomisum.quickclick.controller.project.member.vo.MemberPageRespVO;
import io.github.xiaomisum.quickclick.dal.dataobject.project.ProjectMember;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.infra.dal.dataobject.sys.Post;
import xyz.migoo.framework.infra.dal.dataobject.sys.User;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.MPJLambdaWrapperX;

import java.util.List;

@Mapper
public interface MemberJoinMapper extends BaseMapperX<MemberPageReqVO> {

    default PageResult<MemberPageRespVO> selectPage(MemberPageReqVO req) {
        return selectJoinPage(req, MemberPageRespVO.class, new MPJLambdaWrapperX<MemberPageReqVO>()
                .selectAll(ProjectMember.class)
                .selectAs(User::getName, "username")
                .selectAs(Post::getName, "postName")
                .eq(ProjectMember::getProjectId, req.getProjectId())
                .leftJoinX(User.class, on -> on.eq(ProjectMember::getUserId, User::getId))
                .leftJoinX(Post.class, on -> on.eq(ProjectMember::getPostId, Post::getId))
                .orderByDesc(ProjectMember::getId));
    }

    default List<MemberPageRespVO> selectList(String projectId) {
        return selectJoinList(MemberPageRespVO.class, new MPJLambdaWrapperX<MemberPageReqVO>()
                .selectAll(ProjectMember.class)
                .selectAs(User::getName, "username")
                .eq(ProjectMember::getProjectId, projectId)
                .leftJoinX(User.class, on -> on.eq(ProjectMember::getUserId, User::getId))
                .orderByDesc(ProjectMember::getId));
    }

}
