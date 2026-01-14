package uz.java.backendtask.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import uz.java.backendtask.enumeration.NewsStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "news_history")
@Getter
@Setter
public class NewsHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private News news;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by")
    private User changedBy;

    @Column(name = "from_status")
    @Enumerated(EnumType.STRING)
    private NewsStatus fromStatus;

    @Column(name = "to_status")
    @Enumerated(EnumType.STRING)
    private NewsStatus toStatus;

    @Column(name = "diff_json", columnDefinition = "jsonb")
    @Type(JsonType.class)
    private Object diffJson;

    @Column(name = "changed_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime changedAt;
}
