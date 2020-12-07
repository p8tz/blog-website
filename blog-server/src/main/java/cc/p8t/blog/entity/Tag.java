package cc.p8t.blog.entity;

import cc.p8t.blog.validation.ArticleAdd;
import cc.p8t.blog.validation.TagAdd;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/9/28 10:02
 */
public class Tag {
    @NotNull(groups = {ArticleAdd.class})
    private Integer id;

    @NotBlank(groups = {TagAdd.class})
    @Length(min = 1, max = 8, groups = {TagAdd.class})
    private String tagname;

    private List<Article> articles;
    private User user;

    public Tag() {
    }

    public Tag(Integer id, String tagname, List<Article> articles, User user) {
        this.id = id;
        this.tagname = tagname;
        this.articles = articles;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", tagname='" + tagname + '\'' +
                ", articles=" + articles +
                ", user=" + user +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
