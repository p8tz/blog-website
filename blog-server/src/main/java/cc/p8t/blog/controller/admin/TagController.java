package cc.p8t.blog.controller.admin;

import cc.p8t.blog.entity.Tag;
import cc.p8t.blog.entity.User;
import cc.p8t.blog.result.Result;
import cc.p8t.blog.result.CodeInfo;
import cc.p8t.blog.service.TagService;
import cc.p8t.blog.utils.JWTUtil;
import cc.p8t.blog.validation.TagAdd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
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
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public Result<Object> getAllTags(HttpServletRequest request) {
        String token = request.getHeader("token");
        List<Tag> tags = tagService.findByUserId(JWTUtil.getUserId(token));
        return new Result<>(CodeInfo.SUCCESS, tags);
    }

    @PostMapping("/tag")
    public Result<Object> insertTag(@Validated(TagAdd.class) @RequestBody Tag tag, BindingResult br, HttpServletRequest request) {
        // 插入新tag, tag名称不能为空且长度限制为[1, 8]
        if (br.hasFieldErrors()) {
            return new Result<>(CodeInfo.VALIDATED_ERROR);
        }
        Integer userId = JWTUtil.getUserId(request.getHeader("token"));
        tag.setUser(new User(userId, "", ""));
        tagService.insertTag(tag);
        return new Result<>(CodeInfo.SUCCESS);
    }

    @DeleteMapping("/tag/{id}")
    public Result<Object> deleteTag(@PathVariable("id") Integer tagId) {
        tagService.deleteById(tagId);
        return new Result<>(CodeInfo.SUCCESS);
    }

    @PutMapping("/tag/{id}")
    public Result<Object> updateTag(@PathVariable("id") Integer tagId,
                                         @RequestBody Tag tag) {
        tag.setId(tagId);
        tagService.updateById(tag);
        return new Result<>(CodeInfo.SUCCESS);
    }
}
