package uz.java.backendtask.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.java.backendtask.enumeration.AdsCreativeType;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class AdsCreativeCreateDTO  implements Serializable {

    @NotNull(message = "campaign-id is null")
    private Long campaignId;

    @NotNull(message = "type is null")
    private AdsCreativeType type;

    private String landingUrl;

    private Long mediaId;

    private String htmlSnippet;

    @NotNull(message = "is-active is null")
    private Boolean isActive;

    @Valid
    @NotNull(message = "translation is null")
    @Size(min = 3,max = 3,message = "translation min 3 max 3 languages required")
    private List<AdsCreativeTranslationRequestDTO> translations;
}
