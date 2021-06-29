package personal.blog.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String title;

    private String body;
    @Column(columnDefinition = "TIMESTAMP",name = "created_time")
    private LocalDateTime creationTime;

    @Column(columnDefinition = "TIMESTAMP",name = "modification_time")
    private LocalDateTime modificationTime;

    @Column(columnDefinition = "TIMESTAMP",name = "publishion_time")
    private LocalDateTime publishTime;

    @Column(name = "is_published")
    private boolean isPublished;

    @ManyToMany(fetch = FetchType.LAZY,
               cascade = {CascadeType.MERGE,CascadeType.PERSIST,
                       CascadeType.PERSIST,CascadeType.DETACH}
    )
    @JoinTable(name="article_tag",
    joinColumns =@JoinColumn(name="article_id"),
    inverseJoinColumns =@JoinColumn(name="tag_id"))
    private List<Tag> tags;

}
