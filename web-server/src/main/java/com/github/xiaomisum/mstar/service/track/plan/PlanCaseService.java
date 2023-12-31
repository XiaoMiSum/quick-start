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

package com.github.xiaomisum.mstar.service.track.plan;

import com.github.xiaomisum.mstar.controller.track.plan.vo.PlanCaseExecuteVO;
import com.github.xiaomisum.mstar.controller.track.plan.vo.PlanCaseQueryReqVO;
import com.github.xiaomisum.mstar.dal.dataobject.track.PlanCase;
import com.github.xiaomisum.mstar.enums.TestStatus;
import com.github.xiaomisum.mstar.model.dto.Statistics;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface PlanCaseService {

    PageResult<PlanCase> getPage(PlanCaseQueryReqVO req);

    PlanCase get(String id);

    List<PlanCase> getList(String planId);

    List<PlanCase> getList(String planId, TestStatus result);

    List<PlanCase> getListGtId(String opt, String planId, String id);

    void add(List<PlanCase> cases);

    void update(PlanCase planCase);

    void remove(String id);

    void remove(List<String> ids);

    Statistics statistics(String planId);

    void execute(PlanCaseExecuteVO execute);

}
