package uz.java.backendtask.service;

import org.springframework.data.domain.Page;
import uz.java.backendtask.dto.*;
import uz.java.backendtask.entity.AdsPlacement;
import uz.java.backendtask.specification.PageRequest;

public interface AdsPlacementService {

    AdsPlacementResponseDTO create(AdsPlacementCreateDTO createDTO);

    AdsPlacementResponseDTO update(Long id, AdsPlacementUpdateDTO updateDTO);

    AdsPlacementResponseDTO updatePatch(Long id, AdsPlacementUpdatePatchDTO updatePatchDTO);

    AdsPlacementResponseDTO getById(Long id);

    Page<AdsPlacementResponseDTO> search(PageRequest request, AdsPlacementSearchCriteria criteria);

    AdsPlacement findById(Long id);

    void deleteById(Long id);
}
