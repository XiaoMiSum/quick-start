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

import io.github.xiaomisum.quickclick.dal.dataobject.quality.CaseReuseRecord;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface CaseReuseService {

    /**
     * 记录测试用例复用操作
     *
     * @param record 复用记录
     * @return 记录ID
     */
    Long addRecord(CaseReuseRecord record);

    /**
     * 获取复用记录列表
     *
     * @param originalCaseId 原始用例ID
     * @return 复用记录列表
     */
    List<CaseReuseRecord> getRecords(String originalCaseId);

    /**
     * 获取复用记录分页列表
     *
     * @param targetId   目标ID
     * @param targetType 目标类型
     * @return 复用记录分页列表
     */
    PageResult<CaseReuseRecord> getPage(String targetId, String targetType);

    /**
     * 统计用例复用次数
     *
     * @param originalCaseId 原始用例ID
     * @return 复用次数
     */
    Integer countReuse(String originalCaseId);

    /**
     * 获取最常复用的用例列表
     *
     * @param projectId 项目ID
     * @param limit     限制数量
     * @return 最常复用的用例列表
     */
    List<CaseReuseStatistics> getMostReusedCases(String projectId, Integer limit);
}