package uz.java.backendtask.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ads_placements")
@Getter
@Setter
public class AdsPlacement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    private String title;
    private String description;

    @Column(name = "is_active")
    private Boolean isActive;
}
