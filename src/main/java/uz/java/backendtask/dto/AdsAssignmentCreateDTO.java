package uz.java.backendtask.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AdsAssignmentCreateDTO  implements Serializable {

    @NotNull(message = "placement-id is null")
    private Long placementId;

    @NotNull(message = "campaign-id is null")
    private Long campaignId;

    @NotNull(message = "creative-id is null")
    private Long creativeId;

    private Integer weight;


    private Object langFilter;


    private Object categoryFilter;

    @NotNull(message = "start-at is null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endAt;

    private Boolean isActive;
}
