package cc.p8t.blog.service;

import cc.p8t.blog.entity.Article;

import java.util.List;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/9/28 14:58
 */
public interface ArticleService {
    List<Article> findByUserId(Integer id);

    void insertArticle(Article article);

    void updateArticle(Article article);

    Article findById(Integer articleId);

    int deleteById(Integer articleId);
}
