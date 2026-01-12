package uz.java.backendtask.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ads_creative_translations")
@Getter
@Setter
public class AdsCreativeTranslation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creative_id")
    private AdsCreative creative;

    private String lang;

    private String title;

    @Column(name = "alt_text")
    private String altText;
}
