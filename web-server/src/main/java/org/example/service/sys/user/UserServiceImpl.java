package org.example.service.sys.user;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.example.controller.login.vo.PasswordVO;
import org.example.controller.sys.user.vo.UserQueryReqVO;
import org.example.dal.dataobject.sys.User;
import org.example.dal.mapper.sys.UserMapper;
import org.example.enums.ErrorCodeConstants;
import org.example.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.exception.util.ServiceExceptionUtil;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;
import java.util.Objects;

import static org.example.enums.ErrorCodeConstants.USER_ORIGINAL_PASSWORD_UNCONFORMITY;
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
            throw ServiceExceptionUtil.get(USER_ORIGINAL_PASSWORD_UNCONFORMITY);
        }
        user.setPassword(PasswordUtils.encode(password.getNewPassword()));
        mapper.updateById(user);
    }

}
