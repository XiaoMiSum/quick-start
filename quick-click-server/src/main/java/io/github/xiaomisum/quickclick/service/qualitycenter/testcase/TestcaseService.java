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

package io.github.xiaomisum.quickclick.service.qualitycenter.testcase;

import io.github.xiaomisum.quickclick.controller.quality.testcase.vo.TestcaseQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Testcase;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface TestcaseService {

    PageResult<Testcase> getPage(TestcaseQueryReqVO req);

    List<Testcase> getList(String projectId);

    PageResult<Testcase> getPage(TestcaseQueryReqVO req, List<String> notInIds);

    Testcase get(String id);

    List<Testcase> getList(List<String> ids);

    String add(Testcase testcase);

    void add(List<Testcase> testcases);

    void update(Testcase testcase);

    void remove(List<String> ids);

    void recover(List<String> ids, String projectId);

    void removeTrash(List<String> ids, String projectId);
}
