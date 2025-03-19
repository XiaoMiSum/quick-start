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

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.github.xiaomisum.quickclick.dal.dataobject.project.ProjectNode;
import io.github.xiaomisum.quickclick.dal.mapper.project.NodeMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NodeServiceImpl implements NodeService {
    
    @Resource
    private NodeMapper mapper;

    @Override
    public ProjectNode get(String id) {
        return mapper.selectById(id);
    }

    @Override
    public List<ProjectNode> getList(String projectId) {
        return mapper.selectList(projectId);
    }

    @Override
    public void add(ProjectNode node) {
        node.setPath("/" + node.getName());
        if (StrUtil.isNotBlank(node.getParentId())) {
            node.setPath(mapper.selectById(node.getParentId()).getPath() + node.getPath());
        }
        mapper.insert(node);
    }

    @Override
    public void update(ProjectNode node) {
        node.setPath("/" + node.getName());
        if (StrUtil.isNotBlank(node.getParentId())) {
            node.setPath(mapper.selectById(node.getParentId()).getPath() + node.getPath());
        }
        mapper.updateById(node);
    }

    @Override
    public void remove(List<String> ids) {
        List<String> canRemoveIds = ids.stream()
                .filter(parentId -> CollectionUtil.isEmpty(mapper.selectChildren(parentId)))
                .toList();
        // todo 验证是否有用例
        mapper.deleteByIds(canRemoveIds);
    }
}
