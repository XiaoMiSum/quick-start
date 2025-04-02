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

import com.google.common.collect.Lists;
import io.github.xiaomisum.quickclick.controller.quality.plan.vo.PlanAddReqVO;
import io.github.xiaomisum.quickclick.controller.quality.plan.vo.PlanPageRespVO;
import io.github.xiaomisum.quickclick.controller.quality.plan.vo.PlanRespVO;
import io.github.xiaomisum.quickclick.controller.quality.plan.vo.PlanUpdateReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.Plan;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.PlanCase;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.SimpleData;

import java.util.List;
import java.util.Objects;

@Mapper
public interface PlanConvert {

    PlanConvert INSTANCE = Mappers.getMapper(PlanConvert.class);

    Plan convert(PlanAddReqVO req);

    Plan convert(PlanUpdateReqVO req);

    PlanRespVO convert(Plan plan);

    PageResult<PlanPageRespVO> convert(PageResult<Plan> beans);

    default List<SimpleData> convert(List<Plan> beans) {
        if (Objects.isNull(beans)) {
            return null;
        }
        List<SimpleData> results = Lists.newArrayList();
        beans.forEach(item -> results.add(new SimpleData(item.getId(), item.getTitle())));
        return results;
    }

    default List<SimpleData> convert1(List<PlanCase> beans) {
        if (Objects.isNull(beans)) {
            return null;
        }
        List<SimpleData> results = Lists.newArrayList();
        beans.forEach(item -> results.add(new SimpleData(item.getOriginalId(), item.getTitle())));
        return results;
    }
}
