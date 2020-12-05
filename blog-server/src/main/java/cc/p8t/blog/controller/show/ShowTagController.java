package cc.p8t.blog.controller.show;

import cc.p8t.blog.entity.Tag;
import cc.p8t.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/12/5 17:19
 */
@RestController
public class ShowTagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/user/{userId}/tags")
    public Map<String, Object> getTagList(@PathVariable("userId") int userId) {
        List<Tag> tags = tagService.findByUserId(userId);
        return Map.of("tagList", tags);
    }
}
