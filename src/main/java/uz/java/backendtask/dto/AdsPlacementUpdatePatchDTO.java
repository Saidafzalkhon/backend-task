package uz.java.backendtask.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class AdsPlacementUpdatePatchDTO  implements Serializable {

    private String code;

    private String title;

    private String description;

    private Boolean isActive;
}
