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

package io.github.xiaomisum.quickclick.service.ai;

import io.github.xiaomisum.quickclick.dal.dataobject.ai.AiGeneratedTestcase;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.security.core.LoginUser;

import java.util.List;

public interface AiGeneratedTestcaseService {

    /**
     * 创建AI生成的测试用例
     *
     * @param testcase AI生成的测试用例信息
     * @param user     当前用户
     * @return AI生成的测试用例ID
     */
    Long createTestcase(AiGeneratedTestcase testcase, LoginUser user);

    /**
     * 更新AI生成的测试用例
     *
     * @param testcase AI生成的测试用例信息
     * @param user     当前用户
     * @return 是否更新成功
     */
    boolean updateTestcase(AiGeneratedTestcase testcase, LoginUser user);

    /**
     * 删除AI生成的测试用例
     *
     * @param id   AI生成的测试用例ID
     * @param user 当前用户
     * @return 是否删除成功
     */
    boolean deleteTestcase(Long id, LoginUser user);

    /**
     * 获取AI生成的测试用例详情
     *
     * @param id AI生成的测试用例ID
     * @return AI生成的测试用例信息
     */
    AiGeneratedTestcase getTestcase(Long id);

    /**
     * 获取AI生成的测试用例列表
     *
     * @param requirementId 需求ID
     * @param status        状态
     * @return AI生成的测试用例列表
     */
    List<AiGeneratedTestcase> getTestcaseList(String requirementId, Integer status);

    /**
     * 分页获取AI生成的测试用例列表
     *
     * @param requirementId 需求ID
     * @param status        状态
     * @param pageNo        页码
     * @param pageSize      每页数量
     * @return AI生成的测试用例分页列表
     */
    PageResult<AiGeneratedTestcase> getTestcasePage(String requirementId, Integer status, Integer pageNo,
            Integer pageSize);

    /**
     * 确认AI生成的测试用例
     *
     * @param id   AI生成的测试用例ID
     * @param user 当前用户
     * @return 是否确认成功
     */
    boolean confirmTestcase(Long id, LoginUser user);

    /**
     * 拒绝AI生成的测试用例
     *
     * @param id   AI生成的测试用例ID
     * @param user 当前用户
     * @return 是否拒绝成功
     */
    boolean rejectTestcase(Long id, LoginUser user);

    /**
     * 导入AI生成的测试用例到测试管理系统
     *
     * @param ids  AI生成的测试用例ID列表
     * @param user 当前用户
     * @return 是否导入成功
     */
    boolean importTestcases(List<Long> ids, LoginUser user);
}