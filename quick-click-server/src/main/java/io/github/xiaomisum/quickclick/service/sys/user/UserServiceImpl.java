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

package io.github.xiaomisum.quickclick.service.sys.user;

import cn.hutool.core.util.StrUtil;
import io.github.xiaomisum.quickclick.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import io.github.xiaomisum.quickclick.controller.login.vo.PasswordVO;
import io.github.xiaomisum.quickclick.controller.sys.user.vo.UserQueryReqVO;
import io.github.xiaomisum.quickclick.dal.dataobject.sys.User;
import io.github.xiaomisum.quickclick.dal.mapper.sys.UserMapper;
import io.github.xiaomisum.quickclick.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.exception.util.ServiceExceptionUtil;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;
import java.util.Objects;

import static xyz.migoo.framework.common.enums.NumberConstants.N_0;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper mapper;
    @Value("${migoo.security.token-secret}")
    private String secret;

    @Override
    public PageResult<User> getPage(UserQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public List<User> get(Integer... status) {
        return mapper.selectList(Objects.isNull(status) || status.length < 1 ? null : status[0]);
    }

    @Override
    public User get(String username) {
        User user = mapper.selectByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    @Override
    public User get(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public void add(User user) {
        user.setPassword(PasswordUtils.encode(user.getPassword()));
        mapper.insert(user);
    }

    @Override
    public void update(User user) {
        if (StrUtil.isNotBlank(user.getPassword())) {
            user.setPassword(PasswordUtils.encode(user.getPassword()));
        }
        if (mapper.updateById(user) == N_0) {
            throw ServiceExceptionUtil.get(ErrorCodeConstants.USER_NOT_EXISTS);
        }
    }

    @Override
    public void remove(Long id) {
        if (mapper.deleteById(id) == N_0) {
            throw ServiceExceptionUtil.get(ErrorCodeConstants.USER_NOT_EXISTS);
        }
    }

    @Override
    public void verify(String username) {
        if (Objects.nonNull(mapper.selectByUsername(username))) {
            throw ServiceExceptionUtil.get(ErrorCodeConstants.USER_IS_EXISTS);
        }
    }

    @Override
    public void update(PasswordVO password) {
        User user = mapper.selectById(password.getId());
        if (!PasswordUtils.verify(password.getOldPassword(), user.getPassword())) {
            throw ServiceExceptionUtil.get(ErrorCodeConstants.USER_ORIGINAL_PASSWORD_UNCONFORMITY);
        }
        user.setPassword(PasswordUtils.encode(password.getNewPassword()));
        mapper.updateById(user);
    }

}
