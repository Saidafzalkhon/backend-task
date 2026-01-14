package uz.java.backendtask.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.java.backendtask.enumeration.AdsCreativeType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ads_creative")
@Getter
@Setter
public class AdsCreative extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id")
    private AdsCampaign campaign;

    @Enumerated(EnumType.STRING)
    private AdsCreativeType type;

    @Column(name = "landing_url")
    private String landingUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_media_id")
    private Media imageMedia;

    @Column(name = "html_snippet", columnDefinition = "text")
    @Lob
    private String htmlSnippet;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "creative", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdsCreativeTranslation> translations = new ArrayList<>();

}
