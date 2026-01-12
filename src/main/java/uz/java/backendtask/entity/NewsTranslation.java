package uz.java.backendtask.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table(name = "news_translations")
@Getter
@Setter
public class NewsTranslation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private News news;

    private String lang;
    private String title;
    private String slug;

    private String summary;

    @Lob
    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "meta_title")
    private String metaTitle;

    @Column(name = "meta_description")
    private String metaDescription;


    @Column(name = "search_fts", columnDefinition = "tsvector")
    @ColumnTransformer(write = "to_tsvector(?)")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String searchFts;


    @PrePersist
    @PreUpdate
    public void updateFTSVector() {
        this.searchFts = String.join(" ",
                this.title != null ? this.title : "",
                this.summary != null ? this.summary : "",
                this.content != null ? this.content : ""
        );
    }
}
