package cc.p8t.blog.service;

import cc.p8t.blog.entity.Type;
import cc.p8t.blog.mapper.TypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/9/29 16:57
 */
@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeMapper typeMapper;

    @Override
    public List<Type> findByUserId(Integer id) {
        return typeMapper.findByUserId(id);
    }

    @Override
    public int insertType(Type type) {
        return typeMapper.insertType(type);
    }

    @Override
    public Type findById(Integer id) {
        return typeMapper.findById(id);
    }

    @Override
    public int deleteById(Integer id) {
        return typeMapper.deleteById(id);
    }

    @Override
    public int updateById(Type type) {
        return typeMapper.updateById(type);
    }

    @Override
    public boolean existInArticle(Integer typeId) {
        return typeMapper.existInArticle(typeId);
    }

    @Override
    public Type findByName(Integer userId, String typeName) {
        return typeMapper.findByName(userId, typeName);
    }
}
