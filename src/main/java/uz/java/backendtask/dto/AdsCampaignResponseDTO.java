package uz.java.backendtask.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import uz.java.backendtask.enumeration.AdsCampaignStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AdsCampaignResponseDTO  implements Serializable {

    private Long id;

    @NotBlank(message = "name is blank")
    private String name;

    private String advertiser;

    @NotNull(message = "status is null")
    private AdsCampaignStatus status;

    @NotNull(message = "start-at is null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endAt;

    private Integer dailyCapImpressions;

    private Integer dailyCapClicks;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
