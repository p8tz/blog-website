package cc.p8t.blog.controller.admin;

import cc.p8t.blog.entity.User;
import cc.p8t.blog.service.UserService;
import cc.p8t.blog.utils.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/9/27 14:52
 */

@RestController
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Map<String, Object> checkUser(@RequestBody User user) {
        User u = userService.checkUser(user);
        Map<String, Object> response = new HashMap<>();
        if (u != null) {
            response.put("status", 200);
            Map<String, String> info = new HashMap<>();
            info.put("userId", u.getId() + "");
            info.put("username", u.getUsername());
            String token = JWTUtil.getToken(info);
            response.put("token", token);
        } else {
            response.put("status", 404);
        }
        return response;
    }
}
