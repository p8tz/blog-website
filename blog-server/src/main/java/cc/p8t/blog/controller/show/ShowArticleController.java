package cc.p8t.blog.controller.show;

import cc.p8t.blog.entity.Article;
import cc.p8t.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/12/2 22:21
 */
@RestController
public class ShowArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/user/{userId}")
    public Map<String, Object> findArticleListByUserId(@PathVariable("userId") int id) {
        List<Article> articles = articleService.findByUserId(id);
        return Map.of("articleList", articles);
    }

    @GetMapping("/user/{userId}/article/{articleId}")
    public Map<String, Object> findArticleById(@PathVariable("userId") int userId,
                                               @PathVariable("articleId") int articleId) {
        Article article = articleService.findById(articleId);
        return Map.of("article", article);
    }

    @GetMapping("/user/{userId}/type/{typeId}")
    public Map<String, Object> getArticleByType(@PathVariable("userId") int userId,
                                                @PathVariable("typeId") int typeId) {
        List<Article> articles = articleService.findByTypeId(userId, typeId);
        return Map.of("articleList", articles);
    }

    @GetMapping("/user/{userId}/tag/{tagId}")
    public Map<String, Object> getArticleByTag(@PathVariable("userId") int userId,
                                               @PathVariable("tagId") int tagId) {
        List<Article> articles = articleService.findByTagId(userId, tagId);
        return Map.of("articleList", articles);
    }
}
