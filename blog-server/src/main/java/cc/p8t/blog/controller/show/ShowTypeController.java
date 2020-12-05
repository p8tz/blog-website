package cc.p8t.blog.controller.show;

import cc.p8t.blog.entity.Type;
import cc.p8t.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/12/4 20:23
 */
@RestController
public class ShowTypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/user/{userId}/types")
    public Map<String, Object> getTypeList(@PathVariable("userId") int id) {
        List<Type> types = typeService.findByUserId(id);
        return Map.of("typeList", types);
    }
}
