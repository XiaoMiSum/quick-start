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

import io.github.xiaomisum.quickclick.dal.dataobject.ai.AiAgent;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.security.core.LoginUser;

import java.util.List;

public interface AiAgentService {

    /**
     * 创建AI智能体
     *
     * @param agent AI智能体信息
     * @param user  当前用户
     * @return AI智能体ID
     */
    Long createAgent(AiAgent agent, LoginUser user);

    /**
     * 更新AI智能体
     *
     * @param agent AI智能体信息
     * @param user  当前用户
     * @return 是否更新成功
     */
    boolean updateAgent(AiAgent agent, LoginUser user);

    /**
     * 删除AI智能体
     *
     * @param id   AI智能体ID
     * @param user 当前用户
     * @return 是否删除成功
     */
    boolean deleteAgent(Long id, LoginUser user);

    /**
     * 获取AI智能体详情
     *
     * @param id AI智能体ID
     * @return AI智能体信息
     */
    AiAgent getAgent(Long id);

    /**
     * 获取AI智能体列表
     *
     * @param name   智能体名称（模糊查询）
     * @param status 状态
     * @return AI智能体列表
     */
    List<AiAgent> getAgentList(String name, Integer status);

    /**
     * 分页获取AI智能体列表
     *
     * @param name     智能体名称（模糊查询）
     * @param status   状态
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @return AI智能体分页列表
     */
    PageResult<AiAgent> getAgentPage(String name, Integer status, Integer pageNo, Integer pageSize);
}