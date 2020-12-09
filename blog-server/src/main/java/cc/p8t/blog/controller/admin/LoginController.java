package cc.p8t.blog.controller.admin;

import cc.p8t.blog.entity.User;
import cc.p8t.blog.result.CodeInfo;
import cc.p8t.blog.result.Result;
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
    public Result<Map<String, Object>> checkUser(@RequestBody User user) {
        Result<Map<String, Object>> res = new Result<>();
        User u = userService.checkUser(user);
        if (u != null) {
            res.SetCodeInfo(CodeInfo.SUCCESS);
            // 把用户信息写入payload, 获取加密后的token
            Map<String, String> info = new HashMap<>();
            info.put("userId", u.getId() + "");
            info.put("username", u.getUsername());
            String token = JWTUtil.getToken(info);
            res.setData(Map.of("token", token));
        } else {
            res.SetCodeInfo(CodeInfo.AUTH_ERROR);
        }
        return res;
    }

    @PostMapping("/register")
    public Result<Object> register(@RequestBody User user) {
        boolean b = userService.register(user);
        if (!b) return new Result<>(CodeInfo.REPEAT_ADD_ERROR);
        return new Result<>(CodeInfo.SUCCESS);
    }
}
