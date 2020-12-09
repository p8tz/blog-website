package cc.p8t.blog.service;

import cc.p8t.blog.entity.User;
import cc.p8t.blog.mapper.UserMapper;
import cc.p8t.blog.utils.SHA256Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/9/27 14:54
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User checkUser(User user) {
        user.setPassword(SHA256Util.getDigest(user.getPassword()));
        return userMapper.findByUsernameAndPassword(user);
    }

    @Override
    public List<User> findUserList() {
        return userMapper.findUserList();
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public boolean register(User user) {
        User u = findByUsername(user.getUsername());
        if (u != null) return false;
        user.setPassword(SHA256Util.getDigest(user.getPassword()));
        return userMapper.insertUser(user);
    }
}
