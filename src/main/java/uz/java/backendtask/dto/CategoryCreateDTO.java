package uz.java.backendtask.dto;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class CategoryCreateDTO  implements Serializable {
    private Long parentId;
    private Boolean isActive;
    @Valid
    private List<CategoryTranslationDTO> translations;
}
