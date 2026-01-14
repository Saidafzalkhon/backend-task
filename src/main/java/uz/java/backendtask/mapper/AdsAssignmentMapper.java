package uz.java.backendtask.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import uz.java.backendtask.dto.AdsAssignmentCreateDTO;
import uz.java.backendtask.dto.AdsAssignmentResponseDTO;
import uz.java.backendtask.dto.AdsAssignmentUpdateDTO;
import uz.java.backendtask.dto.AdsAssignmentUpdatePatchDTO;
import uz.java.backendtask.entity.AdsAssignment;

@Mapper(componentModel = "spring")
public abstract class AdsAssignmentMapper {

    @Autowired
    protected AdsPlacementMapper placementMapper;

    @Autowired
    protected AdsCampaignMapper adsCampaignMapper;

    @Autowired
    protected AdsCreativeMapper adsCreativeMapper;

    public abstract AdsAssignment toEntity(AdsAssignmentCreateDTO dto);

    @Mapping(target = "placement", expression = "java(placementMapper.toResponse(adsPlacement.getPlacement()))")
    @Mapping(target = "campaign", expression = "java(adsCampaignMapper.toResponse(adsPlacement.getCampaign()))")
    @Mapping(target = "creative", expression = "java(adsCreativeMapper.toResponse(adsPlacement.getCreative()))")
    public abstract AdsAssignmentResponseDTO toResponse(AdsAssignment adsPlacement);

    public Page<AdsAssignmentResponseDTO> toResponse(Page<AdsAssignment> adsPlacements) {
        return adsPlacements.map(this::toResponse);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    public abstract AdsAssignment updateEntity(AdsAssignmentUpdateDTO dto, @MappingTarget AdsAssignment adsPlacement);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract AdsAssignment patchEntity(AdsAssignmentUpdatePatchDTO dto, @MappingTarget AdsAssignment adsPlacement);
}
