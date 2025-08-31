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

package io.github.xiaomisum.quickclick.service.project;

import cn.hutool.core.util.IdUtil;
import io.github.xiaomisum.quickclick.dal.dataobject.project.Requirement;
import io.github.xiaomisum.quickclick.dal.mapper.project.RequirementMapper;
import io.github.xiaomisum.quickclick.controller.project.requirement.vo.RequirementQueryReqVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Service
public class RequirementServiceImpl implements RequirementService {

    @Resource
    private RequirementMapper mapper;

    @Override
    public PageResult<Requirement> getPage(RequirementQueryReqVO reqVO) {
        return mapper.selectPage(reqVO);
    }

    @Override
    public List<Requirement> getList(String projectId) {
        return mapper.selectList(projectId);
    }

    @Override
    public Requirement get(String id) {
        return mapper.selectById(id);
    }

    @Override
    public String add(Requirement requirement) {
        requirement.setId(IdUtil.getSnowflakeNextIdStr());
        mapper.insert(requirement);
        return requirement.getId();
    }

    @Override
    public void update(Requirement requirement) {
        mapper.updateById(requirement);
    }

    @Override
    public void remove(String id) {
        mapper.deleteById(id);
    }
}