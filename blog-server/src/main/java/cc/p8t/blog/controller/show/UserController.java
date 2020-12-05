package cc.p8t.blog.controller.show;

import cc.p8t.blog.entity.User;
import cc.p8t.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/12/4 8:10
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public Map<String, Object> getArticleList() {
        List<User> userList = userService.findUserList();
        return Map.of("userList", userList);
    }
}
