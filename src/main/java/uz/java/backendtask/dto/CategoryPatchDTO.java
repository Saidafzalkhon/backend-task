package uz.java.backendtask.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class CategoryPatchDTO  implements Serializable {
    private Long parentId;
    private Boolean isActive;
    private List<CategoryTranslationDTO> translations;
}
