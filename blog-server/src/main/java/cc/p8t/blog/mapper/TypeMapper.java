package cc.p8t.blog.mapper;

import cc.p8t.blog.entity.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/9/29 16:49
 */
@Mapper
@Repository
public interface TypeMapper {
    List<Type> findByUserId(@Param("userId") Integer id);

    int insertType(@Param("type") Type type);

    int findById(@Param("typeId") Integer id);

    int deleteById(@Param("typeId") Integer id);

    int updateById(@Param("type") Type type);
}
