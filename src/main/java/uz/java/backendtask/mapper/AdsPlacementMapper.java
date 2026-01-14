package uz.java.backendtask.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;
import uz.java.backendtask.dto.AdsPlacementCreateDTO;
import uz.java.backendtask.dto.AdsPlacementResponseDTO;
import uz.java.backendtask.dto.AdsPlacementUpdateDTO;
import uz.java.backendtask.dto.AdsPlacementUpdatePatchDTO;
import uz.java.backendtask.entity.AdsPlacement;

@Mapper(componentModel = "spring")
public interface AdsPlacementMapper {

    AdsPlacement toEntity(AdsPlacementCreateDTO dto);

    AdsPlacementResponseDTO toResponse(AdsPlacement adsPlacement);

    default Page<AdsPlacementResponseDTO> toResponse(Page<AdsPlacement> adsPlacements) {
        return adsPlacements.map(this::toResponse);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    AdsPlacement updateEntity(AdsPlacementUpdateDTO dto, @MappingTarget AdsPlacement adsPlacement);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AdsPlacement patchEntity(AdsPlacementUpdatePatchDTO dto, @MappingTarget AdsPlacement adsPlacement);
}
