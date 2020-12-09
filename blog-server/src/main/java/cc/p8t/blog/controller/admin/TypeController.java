package cc.p8t.blog.controller.admin;

import cc.p8t.blog.entity.Type;
import cc.p8t.blog.entity.User;
import cc.p8t.blog.result.Result;
import cc.p8t.blog.result.CodeInfo;
import cc.p8t.blog.service.TypeService;
import cc.p8t.blog.utils.JWTUtil;
import cc.p8t.blog.validation.TypeAdd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
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
    public Result<Object> getAllTypes(HttpServletRequest request) {
        String token = request.getHeader("token");
        List<Type> types = typeService.findByUserId(JWTUtil.getUserId(token));
        Type defType = typeService.findById(100);
        types.add(defType);
        return new Result<>(CodeInfo.SUCCESS, types);
    }

    @PostMapping("/type")
    public Result<Object> insertType(@Validated(TypeAdd.class) @RequestBody Type type,
                                     BindingResult br, HttpServletRequest request) {
        // 参数校验
        if (br.hasFieldErrors()) {
            return new Result<>(CodeInfo.VALIDATED_ERROR);
        }
        // 名称查重
        Integer userId = JWTUtil.getUserId(request.getHeader("token"));
        if (typeService.findByName(userId, type.getTypename()) != null || "default".equals(type.getTypename())) {
            return new Result<>(CodeInfo.REPEAT_ADD_ERROR);
        }
        type.setUser(new User(userId, "", ""));
        typeService.insertType(type);
        return new Result<>(CodeInfo.SUCCESS);
    }

    @DeleteMapping("/type/{id}")
    public Result<Object> deleteType(@PathVariable("id") Integer typeId) {
        // 防止恶意删除default分类 (如通过postman发送请求)
        if (typeId.equals(100) || typeService.existInArticle(typeId)) {
            return new Result<>(CodeInfo.VALIDATED_ERROR);
        }
        typeService.deleteById(typeId);
        return new Result<>(CodeInfo.SUCCESS);
    }

    @PutMapping("/type/{id}")
    public Result<Object> updateType(@PathVariable("id") Integer typeId,
                                     @RequestBody Type type, HttpServletRequest request) {
        // 名称查重
        Integer userId = JWTUtil.getUserId(request.getHeader("token"));
        Type t = typeService.findByName(userId, type.getTypename());
        if (t != null && !t.getId().equals(typeId) || "default".equals(type.getTypename())) {
            return new Result<>(CodeInfo.REPEAT_ADD_ERROR);
        }
        type.setId(typeId);
        typeService.updateById(type);
        return new Result<>(CodeInfo.SUCCESS);
    }
}
