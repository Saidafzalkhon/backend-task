package uz.java.backendtask.service;

import org.springframework.data.domain.Page;
import uz.java.backendtask.dto.*;
import uz.java.backendtask.entity.AdsCreative;
import uz.java.backendtask.specification.PageRequest;

public interface AdsCreativeService {

    AdsCreativeResponseDTO create(AdsCreativeCreateDTO createDTO);

    AdsCreativeResponseDTO update(Long id, AdsCreativeUpdateDTO updateDTO);

    AdsCreativeResponseDTO updatePatch(Long id, AdsCreativeUpdatePatchDTO updatePatchDTO);

    AdsCreativeResponseDTO getById(Long id);

    Page<AdsCreativeResponseDTO> search(PageRequest request, AdsCreativeSearchCriteria criteria);

    AdsCreative findById(Long id);

    void deleteById(Long id);
}
