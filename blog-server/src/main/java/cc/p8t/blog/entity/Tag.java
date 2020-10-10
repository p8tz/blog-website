package cc.p8t.blog.entity;

import java.util.List;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/9/28 10:02
 */
public class Tag {
    private Integer id;
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
