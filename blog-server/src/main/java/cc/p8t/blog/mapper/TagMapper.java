package cc.p8t.blog.mapper;

import cc.p8t.blog.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/9/29 16:52
 */
@Mapper
@Repository
public interface TagMapper {
    List<Tag> findByUserId(@Param("userId") Integer id);

    int insertTag(@Param("tag") Tag tag);

    Tag findById(@Param("tagId") Integer id);

    int deleteById(@Param("tagId") Integer id);

    int updateById(@Param("tag") Tag tag);

    boolean existInArticle(@Param("tagId") Integer tagId);

    Tag findByName(@Param("userId") Integer userId,
                   @Param("tagName") String tagName);
}
