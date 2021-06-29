package personal.blog.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE,CascadeType.PERSIST,
                    CascadeType.PERSIST,CascadeType.DETACH}
    )
    @JoinTable(name="article_tag",
            joinColumns =@JoinColumn(name="tag_id"),
            inverseJoinColumns =@JoinColumn(name="article_id"))
    private List<Article> articles;
}
