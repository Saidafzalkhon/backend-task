package uz.java.backendtask.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.java.backendtask.enumeration.AdsCampaignStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "ads_campaigns")
@Getter
@Setter
public class AdsCampaign extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String advertiser;

    @Enumerated(EnumType.STRING)
    private AdsCampaignStatus status; // DRAFT, ACTIVE, PAUSED, ENDED

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @Column(name = "daily_cap_impressions")
    private Integer dailyCapImpressions;

    @Column(name = "daily_cap_clicks")
    private Integer dailyCapClicks;

}
