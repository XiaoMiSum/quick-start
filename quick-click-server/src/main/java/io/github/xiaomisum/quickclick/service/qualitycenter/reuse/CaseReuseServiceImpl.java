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

package io.github.xiaomisum.quickclick.service.qualitycenter.reuse;

import cn.hutool.core.util.IdUtil;
import io.github.xiaomisum.quickclick.dal.dataobject.quality.CaseReuseRecord;
import io.github.xiaomisum.quickclick.dal.mapper.qualitycenter.CaseReuseRecordMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.query.LambdaQueryWrapperX;

import java.util.List;

@Service
public class CaseReuseServiceImpl implements CaseReuseService {

    @Resource
    private CaseReuseRecordMapper mapper;

    @Override
    public Long addRecord(CaseReuseRecord record) {
        record.setId(IdUtil.getSnowflakeNextId());
        mapper.insert(record);
        return record.getId();
    }

    @Override
    public List<CaseReuseRecord> getRecords(String originalCaseId) {
        return mapper.selectList(new LambdaQueryWrapperX<CaseReuseRecord>()
                .eq(CaseReuseRecord::getOriginalCaseId, originalCaseId)
                .orderByDesc(CaseReuseRecord::getCreateTime));
    }

    @Override
    public PageResult<CaseReuseRecord> getPage(String targetId, String targetType) {
        return mapper.selectPage(new LambdaQueryWrapperX<CaseReuseRecord>()
                .eq(CaseReuseRecord::getTargetId, targetId)
                .eq(CaseReuseRecord::getTargetType, targetType)
                .orderByDesc(CaseReuseRecord::getCreateTime));
    }

    @Override
    public Integer countReuse(String originalCaseId) {
        return mapper.selectCount(new LambdaQueryWrapperX<CaseReuseRecord>()
                .eq(CaseReuseRecord::getOriginalCaseId, originalCaseId));
    }

    @Override
    public List<CaseReuseStatistics> getMostReusedCases(String projectId, Integer limit) {
        // 这里需要联表查询，获取用例标题等信息
        // 由于是示例代码，简化实现
        return mapper.selectMostReusedCases(projectId, limit);
    }
}