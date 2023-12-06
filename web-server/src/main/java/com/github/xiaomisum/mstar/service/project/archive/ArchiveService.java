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
import com.github.xiaomisum.mstar.dal.dataobject.project.ArchiveModule;
import com.github.xiaomisum.mstar.dal.dataobject.project.ArchiveTestcase;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface ArchiveService {


    PageResult<Archive> getPage(ArchiveQueryReqVO req);

    List<ArchiveModule> getModules(String archiveId);

    ArchiveModule getModule(String archiveId, String originalId);

    PageResult<ArchiveTestcase> getTestcasePage(ArchiveTestcaseQueryReqVO req);

    List<ArchiveTestcase> getTestcases(String archiveId);

    void add(Archive archive, List<ArchiveModule> modules, List<ArchiveTestcase> testcases);

    void update(Archive archive);

    void remove(String id);

    ArchiveTestcase getTestcase(String id);
}
