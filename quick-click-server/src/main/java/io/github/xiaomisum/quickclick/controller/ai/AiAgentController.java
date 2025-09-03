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

package io.github.xiaomisum.quickclick.controller.ai;

import io.github.xiaomisum.quickclick.controller.ai.vo.AiAgentVO;
import io.github.xiaomisum.quickclick.dal.dataobject.ai.AiAgent;
import io.github.xiaomisum.quickclick.service.ai.AiAgentService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.annotation.CurrentUser;

/**
 * AI智能体管理
 */
@RestController
@RequestMapping("/ai/agent")
public class AiAgentController {

    @Resource
    private AiAgentService service;

    /**
     * 创建AI智能体
     *
     * @param data AI智能体信息
     * @param user 当前用户
     * @return AI智能体ID
     */
    @PostMapping
    public Result<Long> create(@RequestBody @Valid AiAgentVO data, @CurrentUser LoginUser user) {
        AiAgent agent = new AiAgent();
        agent.setName(data.getName());
        agent.setDescription(data.getDescription());
        agent.setModelType(data.getModelType());
        agent.setConfig(data.getConfig());
        agent.setStatus(data.getStatus());
        Long id = service.createAgent(agent, user);
        return Result.getSuccessful(id);
    }

    /**
     * 更新AI智能体
     *
     * @param data AI智能体信息
     * @param user 当前用户
     * @return 处理结果
     */
    @PutMapping
    public Result<Boolean> update(@RequestBody @Valid AiAgentVO data, @CurrentUser LoginUser user) {
        AiAgent agent = new AiAgent();
        agent.setId(data.getId());
        agent.setName(data.getName());
        agent.setDescription(data.getDescription());
        agent.setModelType(data.getModelType());
        agent.setConfig(data.getConfig());
        agent.setStatus(data.getStatus());
        boolean result = service.updateAgent(agent, user);
        return Result.getSuccessful(result);
    }

    /**
     * 删除AI智能体
     *
     * @param id   AI智能体ID
     * @param user 当前用户
     * @return 处理结果
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable("id") Long id, @CurrentUser LoginUser user) {
        boolean result = service.deleteAgent(id, user);
        return Result.getSuccessful(result);
    }

    /**
     * 获取AI智能体详情
     *
     * @param id AI智能体ID
     * @return AI智能体信息
     */
    @GetMapping("/{id}")
    public Result<AiAgent> get(@PathVariable("id") Long id) {
        AiAgent agent = service.getAgent(id);
        return Result.getSuccessful(agent);
    }

    /**
     * 分页获取AI智能体列表
     *
     * @param name     智能体名称（模糊查询）
     * @param status   状态
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @return AI智能体分页列表
     */
    @GetMapping
    public Result<PageResult<AiAgent>> getPage(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageResult<AiAgent> page = service.getAgentPage(name, status, pageNo, pageSize);
        return Result.getSuccessful(page);
    }
}