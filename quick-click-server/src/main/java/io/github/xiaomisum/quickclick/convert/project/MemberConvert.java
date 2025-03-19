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

package io.github.xiaomisum.quickclick.convert.project;

import com.google.common.collect.Lists;
import io.github.xiaomisum.quickclick.controller.project.member.vo.MemberAddReqVO;
import io.github.xiaomisum.quickclick.controller.project.member.vo.MemberPageRespVO;
import io.github.xiaomisum.quickclick.controller.project.member.vo.MemberUpdateReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.project.ProjectMember;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.SimpleData;

import java.util.List;

@Mapper
public interface MemberConvert {

    MemberConvert INSTANCE = Mappers.getMapper(MemberConvert.class);

    ProjectMember convert(MemberAddReqVO bean);

    ProjectMember convert(MemberUpdateReqVO bean);

    PageResult<MemberPageRespVO> convert(PageResult<ProjectMember> beans);

    default List<SimpleData> convert(List<MemberPageRespVO> members) {
        List<SimpleData> result = Lists.newArrayList();
        members.forEach(item -> result.add(new SimpleData(item.getUserId(), item.getUsername())));
        return result;
    }
}
