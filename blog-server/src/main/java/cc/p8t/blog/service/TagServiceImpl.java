package cc.p8t.blog.service;

import cc.p8t.blog.entity.Tag;
import cc.p8t.blog.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/9/29 16:57
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;
    @Override
    public List<Tag> findByUserId(Integer id) {
        return tagMapper.findByUserId(id);
    }

    @Override
    public int insertTag(Tag tag) {
        return tagMapper.insertTag(tag);
    }

    @Override
    public int findById(Integer id) {
        return tagMapper.findById(id);
    }

    @Override
    public int deleteById(Integer id) {
        return tagMapper.deleteById(id);
    }

    @Override
    public int updateById(Tag tag) {
        return tagMapper.updateById(tag);
    }
}
