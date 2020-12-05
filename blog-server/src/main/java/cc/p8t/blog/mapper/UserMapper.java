package cc.p8t.blog.mapper;

import cc.p8t.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/9/27 14:22
 */
@Mapper
@Repository
public interface UserMapper {
    User findByUsernameAndPassword(@Param("user") User user);

    User findById(@Param("id") Integer id);

    List<User> findUserList();
}
