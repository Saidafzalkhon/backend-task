package uz.java.backendtask.service;

import org.springframework.data.domain.Page;
import uz.java.backendtask.dto.*;
import uz.java.backendtask.entity.Tag;
import uz.java.backendtask.specification.PageRequest;

public interface TagService {

    TagResponseDTO create(TagCreateDTO createDTO);

    TagResponseDTO update(Long id, TagUpdateDTO updateDTO);

    TagResponseDTO updatePatch(Long id, TagUpdatePatchDTO updatePatchDTO);

    TagResponseDTO getById(Long id);

    Page<TagResponseDTO> search(PageRequest request, TagSearchCriteria criteria);

    Tag findById(Long id);

    void deleteById(Long id);
}
