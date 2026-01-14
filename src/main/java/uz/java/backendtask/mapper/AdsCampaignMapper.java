package uz.java.backendtask.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;
import uz.java.backendtask.dto.AdsCampaignCreateDTO;
import uz.java.backendtask.dto.AdsCampaignResponseDTO;
import uz.java.backendtask.dto.AdsCampaignUpdateDTO;
import uz.java.backendtask.dto.AdsCampaignUpdatePatchDTO;
import uz.java.backendtask.entity.AdsCampaign;

@Mapper(componentModel = "spring")
public interface AdsCampaignMapper {

    AdsCampaign toEntity(AdsCampaignCreateDTO dto);

    AdsCampaignResponseDTO toResponse(AdsCampaign adsPlacement);

    default Page<AdsCampaignResponseDTO> toResponse(Page<AdsCampaign> adsPlacements) {
        return adsPlacements.map(this::toResponse);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    AdsCampaign updateEntity(AdsCampaignUpdateDTO dto, @MappingTarget AdsCampaign adsPlacement);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AdsCampaign patchEntity(AdsCampaignUpdatePatchDTO dto, @MappingTarget AdsCampaign adsPlacement);
}
