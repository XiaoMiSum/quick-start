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

package io.github.xiaomisum.quickclick.controller.self;

import io.github.xiaomisum.quickclick.controller.self.vo.ProfileVO;
import io.github.xiaomisum.quickclick.convert.profile.ProfileConvert;
import io.github.xiaomisum.quickclick.convert.project.ProjectConvert;
import io.github.xiaomisum.quickclick.dal.dataobject.project.Project;
import io.github.xiaomisum.quickclick.service.profile.ProfileService;
import io.github.xiaomisum.quickclick.service.project.ProjectMemberService;
import io.github.xiaomisum.quickclick.service.project.ProjectService;
import io.github.xiaomisum.quickclick.service.qualitycenter.bug.BugService;
import io.github.xiaomisum.quickclick.service.qualitycenter.plan.PlanService;
import io.github.xiaomisum.quickclick.service.qualitycenter.review.ReviewService;
import io.github.xiaomisum.quickclick.service.qualitycenter.testcase.TestcaseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.common.pojo.SimpleData;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.annotation.CurrentUser;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.github.xiaomisum.quickclick.enums.BugStatus.*;
import static io.github.xiaomisum.quickclick.enums.TestStatus.Preparing;
import static io.github.xiaomisum.quickclick.enums.TestStatus.Processing;

@RestController
@RequestMapping("/self")
public class SelfController {

    @Resource
    private ProjectMemberService projectMember;

    @Resource
    private ProjectService project;

    @Resource
    private TestcaseService testcase;

    @Resource
    private PlanService plan;

    @Resource
    private ReviewService review;

    @Resource
    private BugService bug;

    @Resource
    private ProfileService profile;

    /**
     * 获取当前登录用户的项目列表
     *
     * @return 操作项目列表
     */
    @GetMapping("/project")
    public Result<List<SimpleData>> getProject(@CurrentUser LoginUser user) {
        // 通过过当前登录用户 获取该用户参与的项目列表
        List<String> ids = projectMember.getProjectIds(user.getId());
        if (Objects.isNull(ids) || ids.isEmpty()) {
            return Result.getSuccessful();
        }
        List<Project> projects = project.getList(ids);
        return Result.getSuccessful(ProjectConvert.INSTANCE.convert(projects));
    }

    /**
     * 获取用户配置
     *
     * @return 用户配置
     */
    @GetMapping("/profile")
    public Result<ProfileVO> getProfile(@CurrentUser LoginUser user) {
        return Result.getSuccessful(ProfileConvert.INSTANCE.convert(profile.get(user.getId())));
    }

    /**
     * 保存用户配置
     *
     * @return 操作结果
     */
    @PostMapping("/profile")
    public Result<?> saveProfile(@CurrentUser LoginUser user, @RequestBody ProfileVO profile) {
        profile.setUserId(user.getId());
        this.profile.save(ProfileConvert.INSTANCE.convert(profile));
        return Result.getSuccessful();
    }

    /**
     * 保存用户配置
     *
     * @return 操作结果
     */
    @GetMapping("/todo")
    public Result<?> getTodo(@CurrentUser LoginUser user) {
        Map<String, Long> result = Map.of(
                "plans", plan.count(user.getId(), Preparing, Processing),
                "reviews", review.count(user.getId(), Preparing, Processing),
                "bugs", bug.count(user.getId(), New, Opened, Reopened, Rejected, Fixed)
        );
        return Result.getSuccessful(result);
    }
}
