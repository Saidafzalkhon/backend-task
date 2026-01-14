package uz.java.backendtask.service;

import org.springframework.data.domain.Page;
import uz.java.backendtask.dto.*;
import uz.java.backendtask.entity.AdsAssignment;
import uz.java.backendtask.specification.PageRequest;

public interface AdsAssignmentService {

    AdsAssignmentResponseDTO create(AdsAssignmentCreateDTO createDTO);

    AdsAssignmentResponseDTO update(Long id, AdsAssignmentUpdateDTO updateDTO);

    AdsAssignmentResponseDTO updatePatch(Long id, AdsAssignmentUpdatePatchDTO updatePatchDTO);

    AdsAssignmentResponseDTO getById(Long id);

    Page<AdsAssignmentResponseDTO> search(PageRequest request, AdsAssignmentSearchCriteria criteria);

    AdsAssignment findById(Long id);

    void deleteById(Long id);
}
