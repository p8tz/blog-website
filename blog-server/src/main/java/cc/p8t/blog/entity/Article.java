package cc.p8t.blog.entity;

import cc.p8t.blog.validation.ArticleAdd;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.List;

/**
 * @author jljxvg@foxmail.com
 * @date 2020/9/28 9:59
 */
public class Article {
    private Integer id;
    @NotBlank(groups = ArticleAdd.class)
    private String title;
    @NotBlank(groups = ArticleAdd.class)
    private String content;
    @Past(groups = ArticleAdd.class)
    private Date createTime;
    @Past(groups = ArticleAdd.class)
    private Date updateTime;
    private User user;
    @NotNull(groups = ArticleAdd.class)
    private Type type;
    @NotEmpty(groups = ArticleAdd.class)
    private List<Tag> tags;

    public Article() {
    }

    public Article(Integer id, String title, String content, Date createTime, Date updateTime, User user, Type type, List<Tag> tags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.user = user;
        this.type = type;
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", user=" + user +
                ", type=" + type +
                ", tags=" + tags +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
