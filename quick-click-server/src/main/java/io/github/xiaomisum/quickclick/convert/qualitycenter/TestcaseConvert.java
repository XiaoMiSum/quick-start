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

package io.github.xiaomisum.quickclick.convert.qualitycenter;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import io.github.xiaomisum.quickclick.controller.quality.testcase.vo.*;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Testcase;
import io.github.xiaomisum.quickclick.model.dto.TestcaseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Mapper
public interface TestcaseConvert {

    TestcaseConvert INSTANCE = Mappers.getMapper(TestcaseConvert.class);

    Testcase convert(TestcaseAddReqVO bean);

    Testcase convert(TestcaseUpdateReqVO bean);

    TestcaseRespVO convert1(Testcase bean);

    PageResult<TestcasePageRespVO> convert(PageResult<Testcase> beans);

    default TestcaseDTO convert(Testcase testcase) {
        return new TestcaseDTO().setOriginalId(testcase.getId())
                .setTitle(testcase.getTitle())
                .setPriority(testcase.getPriority())
                .setTags(testcase.getTags())
                .setPrerequisite(testcase.getPrerequisite())
                .setSteps(testcase.getSteps())
                .setNodeId(testcase.getNodeId())
                .setProjectId(testcase.getProjectId())
                .setSupervisor(testcase.getSupervisor());
    }

    List<TestcaseDTO> convert(List<Testcase> testcases);

    default List<TestcaseExportVO> convert1(List<Testcase> testcases) {
        List<TestcaseExportVO> result = Lists.newArrayList();
        testcases.forEach(item -> result.add(convert2(item)));
        return result;
    }

    default TestcaseExportVO convert2(Testcase testcase) {
        return new TestcaseExportVO()
                .setTitle(testcase.getTitle())
                .setNodeId(testcase.getNodeId())
                .setPriority(testcase.getPriority())
                .setTags(StrUtil.join(",", testcase.getTags()))
                .setPrerequisite(testcase.getPrerequisite())
                .setSteps(testcase.getSteps());
    }

    default List<Testcase> convert2(List<TestcaseExportVO> imports) {
        List<Testcase> result = Lists.newArrayList();
        imports.forEach(item -> result.add(convert2(item)));
        return result;
    }

    default Testcase convert2(TestcaseExportVO testcase) {
        return new Testcase()
                .setTitle(testcase.getTitle())
                .setPriority(testcase.getPriority())
                .setTags(StrUtil.split(testcase.getTags(), ","))
                .setPrerequisite(testcase.getPrerequisite())
                .setSteps(testcase.getSteps())
                .setNodeId(testcase.getNodeId());
    }

}
