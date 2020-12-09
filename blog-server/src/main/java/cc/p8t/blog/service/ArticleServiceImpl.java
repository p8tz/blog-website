package cc.p8t.blog.service;

import cc.p8t.blog.entity.Article;
import cc.p8t.blog.entity.Tag;
import cc.p8t.blog.mapper.ArticleMapper;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/9/28 15:02
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Article> findByUserId(Integer id) {
        return articleMapper.findByUserId(id);
    }

    @Override
    public void insertArticle(Article article) {
        // 这返回的不是primaryKey, 而是操作的行数, 返回的key直接注入到形参了
        // [错误]int primaryKey = articleMapper.insertArticle(article);

        articleMapper.insertArticle(article);
        int primaryKey = article.getId();
        for (Tag tag : article.getTags()) {
            articleMapper.establishForeignKeyAssociation(primaryKey, tag.getId());
        }
    }

    @Override
    public void updateArticle(Article article) {
        articleMapper.updateArticle(article);
        articleMapper.removeForeignKeyAssociation(article.getId());
        int articleId = article.getId();
        for (Tag tag : article.getTags()) {
            articleMapper.establishForeignKeyAssociation(articleId, tag.getId());
        }
    }

    @Override
    public Article findById(Integer articleId) {
        return articleMapper.findById(articleId);
    }

    @Override
    public int deleteById(Integer articleId) {
        articleMapper.removeForeignKeyAssociation(articleId);
        return articleMapper.deleteById(articleId);
    }

    @Override
    public List<Article> findByTypeId(Integer userId, Integer typeId) {
        return articleMapper.findByTypeId(userId, typeId);
    }

    @Override
    public List<Article> findByTagId(Integer userId, Integer tagId) {
        return articleMapper.findByTagId(userId, tagId);
    }
}
