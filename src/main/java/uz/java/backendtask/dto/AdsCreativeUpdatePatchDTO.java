package uz.java.backendtask.dto;


import jakarta.validation.Valid;
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
public class AdsCreativeUpdatePatchDTO  implements Serializable {

    private Long campaignId;

    private AdsCreativeType type;

    private String landingUrl;

    private Long mediaId;

    private String htmlSnippet;

    private Boolean isActive;

    @Valid
    @Size(min = 0, max = 3, message = "translation min 3 max 3 languages required")
    private List<AdsCreativeTranslationRequestDTO> translations;
}
