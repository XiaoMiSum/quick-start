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

package io.github.xiaomisum.quickclick.convert.project;

import io.github.xiaomisum.quickclick.controller.project.node.vo.NodeAddReqVO;
import io.github.xiaomisum.quickclick.controller.project.node.vo.NodeRespVO;
import io.github.xiaomisum.quickclick.controller.project.node.vo.NodeUpdateReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.project.ProjectNode;
import io.github.xiaomisum.quickclick.model.dto.SimpleResp;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

@Mapper
public interface NodeConvert {

    NodeConvert INSTANCE = Mappers.getMapper(NodeConvert.class);

    NodeRespVO convert(ProjectNode bean);

    ProjectNode convert(NodeAddReqVO bean);

    ProjectNode convert(NodeUpdateReqVO bean);

    PageResult<NodeRespVO> convert(PageResult<ProjectNode> beans);

    List<NodeRespVO> convert(List<ProjectNode> beans);

    List<SimpleResp> convert1(List<ProjectNode> beans);
}