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

package io.github.xiaomisum.quickclick.service.project;

import cn.hutool.core.util.IdUtil;
import io.github.xiaomisum.quickclick.controller.project.management.vo.ProjectPageRespVO;
import io.github.xiaomisum.quickclick.controller.project.management.vo.ProjectQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.project.Project;
import io.github.xiaomisum.quickclick.dal.mapper.project.ProjectMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Resource
    private ProjectMapper mapper;

    @Override
    public PageResult<ProjectPageRespVO> getPage(ProjectQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public List<Project> getList(List<String> ids) {
        return mapper.selectList(ids);
    }

    @Override
    public Project get(String id) {
        return mapper.selectById(id);
    }

    @Override
    public String add(Project project) {
        project.setId(IdUtil.getSnowflakeNextIdStr());
        mapper.insert(project);
        return project.getId();
    }

    @Override
    public void update(Project project) {
        mapper.updateById(project);
    }

    @Override
    public void remove(List<String> ids) {
        mapper.deleteByIds(ids);
    }
}
