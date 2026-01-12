package uz.java.backendtask.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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
    private String diffJson;

    @Column(name = "changed_at")
    private LocalDateTime changedAt;
}
