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

package com.github.xiaomisum.mstar.service.project.iteration;

import com.github.xiaomisum.mstar.controller.project.iteration.vo.ArchiveTestcaseQueryReqVO;
import com.github.xiaomisum.mstar.controller.project.iteration.vo.IterationQueryReqVO;
import com.github.xiaomisum.mstar.dal.dataobject.project.ArchiveNode;
import com.github.xiaomisum.mstar.dal.dataobject.project.ArchiveTestcase;
import com.github.xiaomisum.mstar.dal.dataobject.project.Iteration;
import com.github.xiaomisum.mstar.dal.mapper.project.ArchiveNodeMapper;
import com.github.xiaomisum.mstar.dal.mapper.project.ArchiveTestcaseMapper;
import com.github.xiaomisum.mstar.dal.mapper.project.IterationMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Service
public class IterationServiceImpl implements IterationService {

    @Resource
    private IterationMapper iterationMapper;
    @Resource
    private ArchiveNodeMapper archiveNodeMapper;
    @Resource
    private ArchiveTestcaseMapper archiveTestcaseMapper;

    @Override
    public Iteration get(String iterationId) {
        return iterationMapper.selectById(iterationId);
    }

    @Override
    public PageResult<Iteration> getPage(IterationQueryReqVO req) {
        return iterationMapper.selectPage(req);
    }

    @Override
    public List<ArchiveNode> getNodes(String iterationId) {
        return archiveNodeMapper.selectList(iterationId);
    }

    @Override
    public ArchiveNode getNode(String iterationId, String originalId) {
        return archiveNodeMapper.selectOne(iterationId, originalId);
    }

    @Override
    public PageResult<ArchiveTestcase> getTestcasePage(ArchiveTestcaseQueryReqVO req) {
        return archiveTestcaseMapper.selectPage(req);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Iteration iteration) {
        iterationMapper.insert(iteration);
    }

    @Override
    public void update(Iteration iteration) {
        iterationMapper.updateById(iteration);
    }

    @Override
    public void remove(String id) {
        iterationMapper.deleteById(id);
    }

    @Override
    public ArchiveTestcase getTestcase(String id) {
        return archiveTestcaseMapper.selectById(id);
    }

    @Override
    public List<ArchiveTestcase> getTestcases(String iterationId, String nodeId) {
        return archiveTestcaseMapper.selectList(iterationId, nodeId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void archive(String iterationId, List<ArchiveNode> archiveNodes, List<ArchiveTestcase> archiveTestcases) {
        iterationMapper.updateById((Iteration) new Iteration().setStatus(0).setId(iterationId));
        archiveNodes.forEach(item -> item.setIterationId(iterationId));
        archiveNodeMapper.insertBatch(archiveNodes);
        archiveTestcases.forEach(item -> item.setIterationId(iterationId));
        archiveTestcaseMapper.insertBatch(archiveTestcases);
    }
}
