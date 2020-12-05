package cc.p8t.blog.service;

import cc.p8t.blog.entity.User;

import java.util.List;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/9/27 14:53
 */
public interface UserService {
    User checkUser(User user);
    List<User> findUserList();
}
