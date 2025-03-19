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

package io.github.xiaomisum.quickclick.service.qualitycenter.plan;

import io.github.xiaomisum.quickclick.controller.quality.plan.vo.PlanCaseExecuteVO;
import io.github.xiaomisum.quickclick.controller.quality.plan.vo.PlanCaseQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.PlanCase;
import io.github.xiaomisum.quickclick.enums.TestStatus;
import io.github.xiaomisum.quickclick.model.dto.Statistics;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface PlanCaseService {

    PageResult<PlanCase> getPage(PlanCaseQueryReqVO req);

    PlanCase get(Long id);

    List<PlanCase> getList(String planId);

    PlanCase getFirst(String reviewId);

    List<PlanCase> getList(String planId, TestStatus result);

    List<PlanCase> getListGtId(String opt, String planId, Long id);

    void add(List<PlanCase> cases);

    void update(PlanCase planCase);

    void remove(List<Long> ids);

    Statistics statistics(String planId);

    void execute(PlanCaseExecuteVO execute);

}
