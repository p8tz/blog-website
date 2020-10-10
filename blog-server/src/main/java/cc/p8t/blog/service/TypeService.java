package cc.p8t.blog.service;

import cc.p8t.blog.entity.Type;

import java.util.List;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/9/29 16:55
 */
public interface TypeService {
    List<Type> findByUserId(Integer id);

    int insertType(Type type);

    int findById(Integer id);

    int deleteById(Integer id);

    int updateById(Type type);
}
