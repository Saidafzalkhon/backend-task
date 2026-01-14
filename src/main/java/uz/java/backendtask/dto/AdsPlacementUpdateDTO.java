package uz.java.backendtask.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class AdsPlacementUpdateDTO  implements Serializable {

    @NotBlank(message = "code is blank")
    private String code;

    @NotBlank(message = "title is blank")
    private String title;

    private String description;

    private Boolean isActive = false;
}
