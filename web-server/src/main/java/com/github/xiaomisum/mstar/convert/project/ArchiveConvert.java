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

package com.github.xiaomisum.mstar.convert.project;

import cn.hutool.core.util.StrUtil;
import com.github.xiaomisum.mstar.controller.project.archive.vo.*;
import com.github.xiaomisum.mstar.dal.dataobject.project.Archive;
import com.github.xiaomisum.mstar.dal.dataobject.project.ArchiveNode;
import com.github.xiaomisum.mstar.dal.dataobject.project.ArchiveTestcase;
import com.github.xiaomisum.mstar.dal.dataobject.track.Testcase;
import com.github.xiaomisum.mstar.dal.dataobject.track.TestcaseNode;
import com.github.xiaomisum.mstar.model.dto.SimpleResp;
import com.google.common.collect.Lists;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Mapper
public interface ArchiveConvert {


    ArchiveConvert INSTANCE = Mappers.getMapper(ArchiveConvert.class);

    PageResult<ArchivePageRespVO> convert(PageResult<Archive> beans);

    ArchiveBaseVO convert(Archive archive);

    Archive convert(ArchiveAddReqVO bean);

    Archive convert(ArchiveUpdateReqVO bean);

    List<SimpleResp> convert(List<ArchiveNode> beans);

    default SimpleResp convert(ArchiveNode bean) {
        return new SimpleResp(bean.getOriginalId(), bean.getName(), bean.getParentId());
    }

    default PageResult<ArchiveTestcasePageRespVO> convert1(PageResult<ArchiveTestcase> beans) {
        return new PageResult<>(convert1(beans.getList()), beans.getTotal());
    }

    default List<ArchiveTestcasePageRespVO> convert1(List<ArchiveTestcase> beans) {
        List<ArchiveTestcasePageRespVO> results = Lists.newArrayList();
        beans.forEach(item -> results.add(convert1(item)));
        return results;
    }

    ArchiveTestcasePageRespVO convert1(ArchiveTestcase bean);

    List<ArchiveNode> convert2(List<TestcaseNode> beans);

    default ArchiveNode convert2(TestcaseNode bean) {
        return new ArchiveNode()
                .setOriginalId(bean.getId())
                .setName(bean.getName())
                .setPath(bean.getPath())
                .setProjectId(bean.getProjectId())
                .setParentId(bean.getParentId())
                .setSort(bean.getSort());
    }

    List<ArchiveTestcase> convert3(List<Testcase> beans);

    default ArchiveTestcase convert3(Testcase bean) {
        return new ArchiveTestcase()
                .setProjectId(bean.getProjectId())
                .setNodeId(bean.getNodeId())
                .setOriginalId(bean.getId())
                .setName(bean.getName())
                .setLevel(bean.getLevel())
                .setMaintainer(bean.getMaintainer())
                .setTags(bean.getTags())
                .setPrerequisite(bean.getPrerequisite())
                .setSteps(bean.getSteps())
                .setReviewed(bean.getReviewed());
    }

    default List<ArchiveTestcaseExportVO> convert4(List<ArchiveTestcase> testcases) {
        List<ArchiveTestcaseExportVO> result = Lists.newArrayList();
        testcases.forEach(item -> result.add(convert4(item)));
        return result;
    }

    default ArchiveTestcaseExportVO convert4(ArchiveTestcase bean) {
        return new ArchiveTestcaseExportVO()
                .setName(bean.getName())
                .setNodeId(bean.getNodeId())
                .setLevel(bean.getLevel())
                .setTags(StrUtil.join(",", bean.getTags()))
                .setPrerequisite(bean.getPrerequisite())
                .setSteps(bean.getSteps());
    }

    ArchiveTestcaseRespVO convert(ArchiveTestcase testcase);
}
