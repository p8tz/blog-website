package cc.p8t.blog.entity;

import cc.p8t.blog.validation.ArticleAdd;
import cc.p8t.blog.validation.TypeAdd;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/9/28 10:00
 */
public class Type {

    @NotNull(groups = {ArticleAdd.class})
    private Integer id;

    @NotBlank(groups = {TypeAdd.class})
    @Length(min = 1, max = 8, groups = {TypeAdd.class})
    private String typename;

    private List<Article> articles;
    private User user;

    public Type() {
    }

    public Type(Integer id, String typename, List<Article> articles, User user) {
        this.id = id;
        this.typename = typename;
        this.articles = articles;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", typename='" + typename + '\'' +
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

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
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
