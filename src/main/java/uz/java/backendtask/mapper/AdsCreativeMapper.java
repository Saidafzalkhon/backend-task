package uz.java.backendtask.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import uz.java.backendtask.dto.*;
import uz.java.backendtask.entity.AdsCreative;
import uz.java.backendtask.entity.AdsCreativeTranslation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class AdsCreativeMapper {

    @Autowired
    protected AdsCampaignMapper campaignMapper;


    public abstract AdsCreative toEntity(AdsCreativeCreateDTO dto);

    @Mapping(target = "campaign",expression = "java(campaignMapper.toResponse(adsPlacement.getCampaign()))")
    public abstract AdsCreativeResponseDTO toResponse(AdsCreative adsPlacement);

    public Page<AdsCreativeResponseDTO> toResponse(Page<AdsCreative> adsPlacements) {
        return adsPlacements.map(this::toResponse);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "translations", ignore = true)
    public abstract AdsCreative updateEntity(AdsCreativeUpdateDTO dto, @MappingTarget AdsCreative adsPlacement);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "translations", ignore = true)
    public abstract AdsCreative patchEntity(AdsCreativeUpdatePatchDTO dto, @MappingTarget AdsCreative adsPlacement);

    public abstract AdsCreativeTranslation toEntity(AdsCreativeTranslationRequestDTO dto);


    @AfterMapping
    public void mergeTranslations(
            AdsCreativeUpdateDTO dto,
            @MappingTarget AdsCreative entity
    ) {

        if (dto.getTranslations() == null) {
            return;
        }

        Map<String, AdsCreativeTranslation> existingMap =
                entity.getTranslations()
                        .stream()
                        .collect(Collectors.toMap(
                                AdsCreativeTranslation::getLang,
                                Function.identity()
                        ));

        Set<String> requestLangs = dto.getTranslations()
                .stream()
                .map(AdsCreativeTranslationRequestDTO::getLang)
                .collect(Collectors.toSet());

        for (AdsCreativeTranslationRequestDTO tDto : dto.getTranslations()) {

            AdsCreativeTranslation translation = existingMap.get(tDto.getLang());

            if (translation != null) {
                translation.setTitle(tDto.getTitle());
                translation.setAltText(tDto.getAltText());
            } else {
                AdsCreativeTranslation newT = toEntity(tDto);
                newT.setCreative(entity);
                entity.getTranslations().add(newT);
            }
        }

        entity.getTranslations().removeIf(
                t -> !requestLangs.contains(t.getLang())
        );
    }



    @AfterMapping
    public void mergeTranslations(
            AdsCreativeUpdatePatchDTO dto,
            @MappingTarget AdsCreative entity
    ) {

        if (dto.getTranslations() == null || dto.getTranslations().isEmpty()) {
            return;
        }

        Map<String, AdsCreativeTranslation> existingMap =
                entity.getTranslations()
                        .stream()
                        .collect(Collectors.toMap(
                                AdsCreativeTranslation::getLang,
                                Function.identity()
                        ));

        for (AdsCreativeTranslationRequestDTO tDto : dto.getTranslations()) {

            AdsCreativeTranslation translation =
                    existingMap.get(tDto.getLang());

            if (translation != null) {
                if (tDto.getTitle() != null) {
                    translation.setTitle(tDto.getTitle());
                }
                if (tDto.getAltText() != null) {
                    translation.setAltText(tDto.getAltText());
                }
            } else {
                AdsCreativeTranslation newT = toEntity(tDto);
                newT.setCreative(entity);
                entity.getTranslations().add(newT);
            }
        }
    }

}
