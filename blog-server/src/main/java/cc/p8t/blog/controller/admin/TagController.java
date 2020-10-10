package cc.p8t.blog.controller.admin;

import cc.p8t.blog.entity.Tag;
import cc.p8t.blog.entity.User;
import cc.p8t.blog.service.TagService;
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
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public Map<String, Object> getAllTags(HttpServletRequest request) {
        String token = request.getHeader("token");
        List<Tag> tags = tagService.findByUserId(JWTUtil.getUserId(token));
        return Map.of("tags", tags);
    }
    @PostMapping("/tag")
    public Map<String, Object> insertTag(@RequestBody Tag tag, HttpServletRequest request) {
        Integer userId = JWTUtil.getUserId(request.getHeader("token"));
        tag.setUser(new User(userId, "", ""));
        tagService.insertTag(tag);
        return Map.of("status", 200);
    }

    @DeleteMapping("/tag/{id}")
    public Map<String, Object> deleteTag(@PathVariable("id") Integer tagId) {
        tagService.deleteById(tagId);
        return Map.of("status", 200);
    }

    @PutMapping("/tag/{id}")
    public Map<String, Object> updateTag(@PathVariable("id") Integer tagId,
                                          @RequestBody Tag tag) {
        tag.setId(tagId);
        tagService.updateById(tag);
        return Map.of("status", 200);
    }
}
