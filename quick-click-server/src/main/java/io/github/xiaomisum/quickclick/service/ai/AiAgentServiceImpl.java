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

import cn.hutool.core.util.IdUtil;
import io.github.xiaomisum.quickclick.dal.dataobject.ai.AiAgent;
import io.github.xiaomisum.quickclick.dal.mapper.ai.AiAgentMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.query.LambdaQueryWrapperX;
import xyz.migoo.framework.security.core.LoginUser;

import java.util.List;

@Service
public class AiAgentServiceImpl implements AiAgentService {

    @Resource
    private AiAgentMapper agentMapper;

    @Override
    public Long createAgent(AiAgent agent, LoginUser user) {
        agent.setId(IdUtil.getSnowflakeNextId());
        agent.setCreator(user.getId());
        agentMapper.insert(agent);
        return agent.getId();
    }

    @Override
    public boolean updateAgent(AiAgent agent, LoginUser user) {
        agent.setCreator(user.getId());
        return agentMapper.updateById(agent) > 0;
    }

    @Override
    public boolean deleteAgent(Long id, LoginUser user) {
        return agentMapper.deleteById(id) > 0;
    }

    @Override
    public AiAgent getAgent(Long id) {
        return agentMapper.selectById(id);
    }

    @Override
    public List<AiAgent> getAgentList(String name, Integer status) {
        return agentMapper.selectList(new LambdaQueryWrapperX<AiAgent>()
                .likeIfPresent(AiAgent::getName, name)
                .eqIfPresent(AiAgent::getStatus, status)
                .orderByDesc(AiAgent::getCreateTime));
    }

    @Override
    public PageResult<AiAgent> getAgentPage(String name, Integer status, Integer pageNo, Integer pageSize) {
        return agentMapper.selectPage(new LambdaQueryWrapperX<AiAgent>()
                .likeIfPresent(AiAgent::getName, name)
                .eqIfPresent(AiAgent::getStatus, status)
                .orderByDesc(AiAgent::getCreateTime), pageNo, pageSize);
    }
}