package cc.p8t.blog.service;

import cc.p8t.blog.entity.Tag;

import java.util.List;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/9/29 16:55
 */
public interface TagService {
    List<Tag> findByUserId(Integer id);

    int insertTag(Tag tag);

    Tag findById(Integer id);

    int deleteById(Integer id);

    int updateById(Tag tag);

    boolean existInArticle(Integer tagId);

    Tag findByName(Integer userId, String tagName);
}
