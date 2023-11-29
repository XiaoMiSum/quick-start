package org.example.service.sys.user;

import org.example.controller.login.vo.PasswordVO;
import org.example.controller.sys.user.vo.UserQueryReqVO;
import org.example.dal.dataobject.sys.User;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

public interface UserService {

    PageResult<User> getPage(UserQueryReqVO req);

    List<User> get(Integer... status);

    User get(String username);

    User get(Long id);

    void add(User user);

    void update(User user);

    void remove(Long id);

    void verify(String username);

    void update(PasswordVO password);

}
