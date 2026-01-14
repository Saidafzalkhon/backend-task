package uz.java.backendtask.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import uz.java.backendtask.enumeration.AdsCreativeType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class AdsCreativeResponseDTO  implements Serializable {

    private Long id;

    private AdsCampaignResponseDTO campaign;

    private AdsCreativeType type;

    private String landingUrl;

    private MediaResponse imageMedia;

    private String htmlSnippet;

    private Boolean isActive;

    private List<AdsCreativeTranslationResponseDTO> translations;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
