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

import cn.hutool.core.collection.CollectionUtil;
import com.github.xiaomisum.mstar.controller.track.plan.vo.PlanCaseExecuteVO;
import com.github.xiaomisum.mstar.controller.track.plan.vo.PlanCaseQueryReqVO;
import com.github.xiaomisum.mstar.dal.dataobject.track.PlanCase;
import com.github.xiaomisum.mstar.dal.mapper.track.PlanCaseMapper;
import com.github.xiaomisum.mstar.enums.ResultEnum;
import com.github.xiaomisum.mstar.model.dto.Statistics;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class PlanCaseServiceImpl implements PlanCaseService {

    @Resource
    private PlanCaseMapper mapper;

    @Override
    public PageResult<PlanCase> getPage(PlanCaseQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public PlanCase get(String id) {
        return mapper.selectById(id);
    }

    @Override
    public List<PlanCase> getList(String planId) {
        return mapper.selectList(planId);
    }

    @Override
    public List<PlanCase> getList(String planId, ResultEnum result) {
        return mapper.selectList(planId, result);
    }

    @Override
    public List<PlanCase> getListGtId(String opt, String planId, String id) {
        return (Objects.equals(opt, "next")) ?
                mapper.selectListByGtId(planId, id) : mapper.selectListByLtId(planId, id);
    }

    @Override
    public void add(List<PlanCase> cases) {
        // 过滤已添加的
        mapper.insertBatch(cases.stream()
                .filter(item -> CollectionUtil.isEmpty(mapper.selectList(item.getPlanId(), item.getCaseId())))
                .toList());
    }

    @Override
    public void update(PlanCase planCase) {
        mapper.updateById(planCase);
    }

    @Override
    public void remove(String id) {
        mapper.deleteById(id);
    }

    @Override
    public void remove(List<String> ids) {
        mapper.deleteBatchIds(ids);
    }

    @Override
    public Statistics statistics(String planId) {
        return mapper.statistics(planId);
    }

    @Override
    public void execute(PlanCaseExecuteVO execute) {
        PlanCase planCase = (PlanCase) new PlanCase()
                .setSteps(execute.getSteps())
                .setExecutor(execute.getExecutor())
                .setExecuteTime(new Date())
                .setExecuteResult(execute.getResult())
                .setId(execute.getId());
        mapper.updateById(planCase);
    }
}
