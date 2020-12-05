package cc.p8t.blog.mapper;

import cc.p8t.blog.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/9/28 14:55
 */
@Mapper
@Repository
public interface ArticleMapper {
    List<Article> findByUserId(@Param("userId") Integer id);

    int insertArticle(@Param("article") Article article);

    int establishForeignKeyAssociation(@Param("articleId") Integer articleId,
                                       @Param("tagId") Integer tagId);

    int updateArticle(@Param("article") Article article);

    int removeForeignKeyAssociation(@Param("articleId") Integer articleId);

    Article findById(@Param("articleId") Integer articleId);

    int deleteById(@Param("articleId") Integer articleId);

    List<Article> findByTypeId(@Param("typeId") Integer typeId);

    List<Article> findByTagId(@Param("tagId") Integer tagId);
}
