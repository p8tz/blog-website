package cc.p8t.blog.controller.admin;

import cc.p8t.blog.entity.Type;
import cc.p8t.blog.entity.User;
import cc.p8t.blog.service.TypeService;
import cc.p8t.blog.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/9/29 16:55
 */
@RestController
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public Map<String, Object> getAllTypes(HttpServletRequest request) {
        String token = request.getHeader("token");
        List<Type> types = typeService.findByUserId(JWTUtil.getUserId(token));
        return Map.of("types", types);
    }

    @PostMapping("/type")
    public Map<String, Object> insertType(@RequestBody Type type, HttpServletRequest request) {
        Integer userId = JWTUtil.getUserId(request.getHeader("token"));
        type.setUser(new User(userId, "", ""));
        typeService.insertType(type);
        return Map.of("status", 200);
    }

    @DeleteMapping("/type/{id}")
    public Map<String, Object> deleteType(@PathVariable("id") Integer typeId) {
        typeService.deleteById(typeId);
        return Map.of("status", 200);
    }

    @PutMapping("/type/{id}")
    public Map<String, Object> updateType(@PathVariable("id") Integer typeId,
                                          @RequestBody Type type) {
        type.setId(typeId);
        typeService.updateById(type);
        return Map.of("status", 200);
    }

}
