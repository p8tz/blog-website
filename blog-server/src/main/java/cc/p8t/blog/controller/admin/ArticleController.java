package cc.p8t.blog.controller.admin;

import cc.p8t.blog.entity.Article;
import cc.p8t.blog.entity.User;
import cc.p8t.blog.result.CodeInfo;
import cc.p8t.blog.result.Result;
import cc.p8t.blog.service.ArticleService;
import cc.p8t.blog.utils.JWTUtil;
import cc.p8t.blog.validation.ArticleAdd;
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
 * @date 2020/9/28 15:04
 */
@RestController
@RequestMapping("/admin")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/articles")
    public Result<Object> getArticlesByUserId(HttpServletRequest request) {
        String token = request.getHeader("token");
        List<Article> articles = articleService.findByUserId(JWTUtil.getUserId(token));
        return new Result<>(CodeInfo.SUCCESS, articles);
    }

    @PostMapping("/article")
    public Result<Object> insertArticle(@Validated(ArticleAdd.class) @RequestBody Article article,
                                        BindingResult br, HttpServletRequest request) {
        if (br.hasFieldErrors()) {
            return new Result<>(CodeInfo.VALIDATED_ERROR);
        }
        Integer userId = JWTUtil.getUserId(request.getHeader("token"));
        article.setUser(new User(userId, "", ""));
        articleService.insertArticle(article);
        return new Result<>(CodeInfo.SUCCESS);
    }

    @GetMapping("/article/{id}")
    public Result<Object> findArticleById(@PathVariable("id") Integer articleId) {
        Article article = articleService.findById(articleId);
        return new Result<>(CodeInfo.SUCCESS, article);
    }

    @PutMapping("/article/{id}")
    public Result<Object> updateArticle(@Validated(ArticleAdd.class) @RequestBody Article article,
                                        BindingResult br, @PathVariable("id") Integer articleId) {
        if (br.hasFieldErrors()) {
            return new Result<>(CodeInfo.VALIDATED_ERROR);
        }
        article.setId(articleId);
        articleService.updateArticle(article);
        return new Result<>(CodeInfo.SUCCESS);
    }

    @DeleteMapping("/article/{id}")
    public Result<Object> deleteArticle(@PathVariable("id") Integer articleId) {
        articleService.deleteById(articleId);
        return new Result<>(CodeInfo.SUCCESS);
    }
}