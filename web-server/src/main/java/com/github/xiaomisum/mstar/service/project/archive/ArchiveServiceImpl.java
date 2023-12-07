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

package com.github.xiaomisum.mstar.service.project.archive;

import com.github.xiaomisum.mstar.controller.project.archive.vo.ArchiveQueryReqVO;
import com.github.xiaomisum.mstar.controller.project.archive.vo.ArchiveTestcaseQueryReqVO;
import com.github.xiaomisum.mstar.dal.dataobject.project.Archive;
import com.github.xiaomisum.mstar.dal.dataobject.project.ArchiveNode;
import com.github.xiaomisum.mstar.dal.dataobject.project.ArchiveTestcase;
import com.github.xiaomisum.mstar.dal.mapper.project.ArchiveMapper;
import com.github.xiaomisum.mstar.dal.mapper.project.ArchiveNodeMapper;
import com.github.xiaomisum.mstar.dal.mapper.project.ArchiveTestcaseMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Service
public class ArchiveServiceImpl implements ArchiveService {

    @Resource
    private ArchiveMapper archiveMapper;
    @Resource
    private ArchiveNodeMapper archiveNodeMapper;
    @Resource
    private ArchiveTestcaseMapper archiveTestcaseMapper;

    @Override
    public Archive get(String projectId, String archiveId) {
        return archiveMapper.selectOne(projectId, archiveId);
    }

    @Override
    public PageResult<Archive> getPage(ArchiveQueryReqVO req) {
        return archiveMapper.selectPage(req);
    }

    @Override
    public List<ArchiveNode> getModules(String archiveId) {
        return archiveNodeMapper.selectList(archiveId);
    }

    @Override
    public ArchiveNode getModule(String archiveId, String originalId) {
        return archiveNodeMapper.selectOne(archiveId, originalId);
    }

    @Override
    public PageResult<ArchiveTestcase> getTestcasePage(ArchiveTestcaseQueryReqVO req) {
        return archiveTestcaseMapper.selectPage(req);
    }

    @Override
    public List<ArchiveTestcase> getTestcases(String archiveId) {
        return archiveTestcaseMapper.selectList(archiveId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Archive archive, List<ArchiveNode> modules, List<ArchiveTestcase> testcases) {
        archiveMapper.insert(archive);
        modules.forEach(item -> item.setArchiveId(archive.getId()));
        archiveNodeMapper.insertBatch(modules);
        testcases.forEach(item -> item.setArchiveId(archive.getId()));
        archiveTestcaseMapper.insertBatch(testcases);
    }

    @Override
    public void update(Archive archive) {
        archiveMapper.updateById(archive);
    }

    @Override
    public void remove(String id) {
        archiveMapper.deleteById(id);
    }

    @Override
    public ArchiveTestcase getTestcase(String id) {
        return archiveTestcaseMapper.selectById(id);
    }
}
