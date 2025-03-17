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

package io.github.xiaomisum.quickclick.convert.project;

import io.github.xiaomisum.quickclick.controller.project.vo.ProjectAddReqVO;
import io.github.xiaomisum.quickclick.controller.project.vo.ProjectRespVO;
import io.github.xiaomisum.quickclick.controller.project.vo.ProjectUpdateReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.project.Project;
import com.google.common.collect.Lists;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.SimpleData;

import java.util.List;
import java.util.Objects;

import static xyz.migoo.framework.common.enums.CommonStatusEnum.ENABLE;

@Mapper
public interface ProjectConvert {

    ProjectConvert INSTANCE = Mappers.getMapper(ProjectConvert.class);

    Project convert(ProjectAddReqVO bean);

    Project convert(ProjectUpdateReqVO bean);

    ProjectRespVO convert(Project bean);

    PageResult<ProjectRespVO> convert(PageResult<Project> beans);

    default List<SimpleData<String>> convert(List<Project> projects) {
        List<SimpleData<String>> result = Lists.newArrayList();

        projects.forEach(item -> result.add(convert1(item)));
        return result;
    }

    default SimpleData<String> convert1(Project project) {
        return new SimpleData<>(project.getId(), project.getName(),
                !Objects.equals(project.getStatus(), ENABLE.getStatus()));
    }
}
