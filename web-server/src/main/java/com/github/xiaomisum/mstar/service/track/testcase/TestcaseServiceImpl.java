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

package com.github.xiaomisum.mstar.service.track.testcase;

import com.github.xiaomisum.mstar.controller.track.testcase.vo.testcase.TestcaseQueryReqVO;
import com.github.xiaomisum.mstar.convert.track.TestcaseConvert;
import com.github.xiaomisum.mstar.dal.dataobject.track.Testcase;
import com.github.xiaomisum.mstar.dal.mapper.track.TestcaseMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Service
public class TestcaseServiceImpl implements TestcaseService {

    @Resource
    private TestcaseMapper mapper;
    @Resource
    private TestcaseRecycleService recycleService;

    @Override
    public PageResult<Testcase> getPage(TestcaseQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public List<Testcase> getList(String projectId) {
        return mapper.selectList(projectId);
    }

    @Override
    public PageResult<Testcase> getPage(TestcaseQueryReqVO req, List<String> notInIds) {
        return mapper.selectPage(req, notInIds);
    }

    @Override
    public Testcase get(String id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Testcase> getList(List<String> ids) {
        return mapper.selectBatchIds(ids);
    }

    @Override
    public String add(Testcase testcase) {
        mapper.insert(testcase);
        return testcase.getId();
    }

    @Override
    public void add(List<Testcase> testcases) {
        mapper.insertBatch(testcases);
    }

    @Override
    public void update(Testcase testcase) {
        mapper.updateById(testcase);
    }

    @Override
    public void remove(List<String> ids) {
        List<Testcase> lists = mapper.selectBatchIds(ids);
        if (!lists.isEmpty()) {
            TestcaseConvert.INSTANCE.convert3(lists).forEach(item -> recycleService.add(item));
        }
        mapper.deleteBatchIds(ids);
    }

    @Override
    public void recover(List<String> ids) {
        mapper.recover(ids);
    }


}
