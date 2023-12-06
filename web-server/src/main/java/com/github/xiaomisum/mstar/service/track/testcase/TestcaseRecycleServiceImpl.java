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

import com.github.xiaomisum.mstar.dal.dataobject.track.TestcaseRecycle;
import jakarta.annotation.Resource;
import com.github.xiaomisum.mstar.controller.track.testcase.vo.testcase.TestcaseQueryReqVO;
import com.github.xiaomisum.mstar.dal.mapper.track.TestcaseRecycleMapper;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Service
public class TestcaseRecycleServiceImpl implements TestcaseRecycleService {

    @Resource
    private TestcaseRecycleMapper mapper;

    @Override
    public PageResult<TestcaseRecycle> getPage(TestcaseQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public List<TestcaseRecycle> getList(List<String> ids) {
        return mapper.selectBatchIds(ids);
    }

    @Override
    public void add(TestcaseRecycle testcase) {
        mapper.insert(testcase);
    }

    @Override
    public void remove(List<String> ids) {
        mapper.deleteBatchIds(ids);
    }
}
