package cc.p8t.blog.controller.admin;

import cc.p8t.blog.entity.Article;
import cc.p8t.blog.entity.User;
import cc.p8t.blog.service.ArticleService;
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
 * @date 2020/9/28 15:04
 */
@RestController
@RequestMapping("/admin")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/articles")
    public Map<String, Object> getArticlesByUserId(HttpServletRequest request) {
        String token = request.getHeader("token");
        List<Article> articles = articleService.findByUserId(JWTUtil.getUserId(token));
        return Map.of("articles", articles);
    }

    @PostMapping("/article")
    public Map<String, Object> insertArticle(@RequestBody Article article, HttpServletRequest request) {
        Integer userId = JWTUtil.getUserId(request.getHeader("token"));
        article.setUser(new User(userId, "", ""));
        articleService.insertArticle(article);
        return Map.of("status", 200);
    }

    @GetMapping("/article/{id}")
    public Map<String, Object> findArticleById(@PathVariable("id") Integer articleId) {
        Article article = articleService.findById(articleId);
        return Map.of("article", article);
    }

    @PutMapping("/article/{id}")
    public Map<String, Object> updateArticle(@RequestBody Article article,
                                             @PathVariable("id") Integer articleId) {
        article.setId(articleId);
        articleService.updateArticle(article);
        return Map.of("status", 200);
    }

    @DeleteMapping("/article/{id}")
    public Map<String, Object> deleteArticle(@PathVariable("id") Integer articleId) {
        articleService.deleteById(articleId);
        return Map.of("status", 200);
    }
}
