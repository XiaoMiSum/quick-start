/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2025.  Lorem XiaoMiSum (mi_xiao@qq.com)
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

package io.github.xiaomisum.quickclick.controller.user;

import io.github.xiaomisum.quickclick.convert.project.ProjectConvert;
import io.github.xiaomisum.quickclick.dal.dataobject.project.Project;
import io.github.xiaomisum.quickclick.service.project.ProjectMemberService;
import io.github.xiaomisum.quickclick.service.project.ProjectService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.common.pojo.SimpleData;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.annotation.CurrentUser;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private ProjectMemberService projectMemberService;

    @Resource
    private ProjectService projectService;

    /**
     * 获取当前登录用户的项目列表
     *
     * @return 操作项目列表
     */
    @GetMapping("/project")
    public Result<List<SimpleData>> getUserProject(@CurrentUser LoginUser user) {
        // 通过过当前登录用户 获取该用户参与的项目列表
        List<String> ids = projectMemberService.getProjectIds(user.getId());
        if (Objects.isNull(ids) || ids.isEmpty()) {
            return Result.getSuccessful();
        }
        List<Project> projects = projectService.getList(ids);
        return Result.getSuccessful(ProjectConvert.INSTANCE.convert(projects));
    }
}
