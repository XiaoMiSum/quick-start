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

package com.github.xiaomisum.mstar.convert.track;

import com.github.xiaomisum.mstar.controller.track.plan.vo.PlanCasePageRespVO;
import com.github.xiaomisum.mstar.controller.track.plan.vo.PlanCaseRespVO;
import com.github.xiaomisum.mstar.dal.dataobject.track.PlanCase;
import com.github.xiaomisum.mstar.dal.dataobject.track.ReviewCase;
import com.github.xiaomisum.mstar.dal.dataobject.track.Testcase;
import com.github.xiaomisum.mstar.model.dto.TestcaseDTO;
import com.google.common.collect.Lists;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Mapper
public interface PlanCaseConvert {

    PlanCaseConvert INSTANCE = Mappers.getMapper(PlanCaseConvert.class);

    PlanCase convert(TestcaseDTO testcase);

    PlanCaseRespVO convert(PlanCase bean);

    List<PlanCase> convert(List<TestcaseDTO> testcases);

    PageResult<PlanCasePageRespVO> convert(PageResult<PlanCase> page);

    PageResult<PlanCasePageRespVO> convert1(PageResult<Testcase> page);

    default PlanCasePageRespVO convert(Testcase bean) {
        return (PlanCasePageRespVO) new PlanCasePageRespVO()
                .setReviewed(bean.getReviewed())
                .setCaseId(bean.getId())
                .setName(bean.getName())
                .setMaintainer(bean.getMaintainer())
                .setTags(bean.getTags())
                .setLevel(bean.getLevel())
                .setNodeId(bean.getNodeId());
    }

    default List<PlanCase> convert(List<ReviewCase> reviewCases, String planId) {
        List<PlanCase> results = Lists.newArrayList();
        reviewCases.forEach(item -> results.add(convert(item, planId)));
        return results;
    }

    default PlanCase convert(ReviewCase reviewCase, String planId) {
        return new PlanCase()
                .setPlanId(planId)
                .setCaseId(reviewCase.getCaseId())
                .setName(reviewCase.getName())
                .setTags(reviewCase.getTags())
                .setLevel(reviewCase.getLevel())
                .setNodeId(reviewCase.getNodeId())
                .setMaintainer(reviewCase.getMaintainer())
                .setPrerequisite(reviewCase.getPrerequisite())
                .setSteps(reviewCase.getSteps());
    }

}
