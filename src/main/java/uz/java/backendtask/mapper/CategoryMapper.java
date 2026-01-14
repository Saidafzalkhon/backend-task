package uz.java.backendtask.mapper;

import org.mapstruct.*;
import org.springframework.data.domain.Page;
import uz.java.backendtask.dto.*;
import uz.java.backendtask.entity.Category;
import uz.java.backendtask.entity.CategoryTranslation;
import uz.java.backendtask.entity.News;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "translations", ignore = true)
    public abstract Category toEntity(CategoryCreateDTO dto);

    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "translations", ignore = true)
    public abstract void updateEntity(
            CategoryUpdateDTO dto,
            @MappingTarget Category entity
    );

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "translations", ignore = true)
    public abstract void patchEntity(
            CategoryPatchDTO dto,
            @MappingTarget Category entity
    );

    @Mapping(source = "parent.id", target = "parentId")
    public abstract CategoryResponseDTO toResponse(Category entity);

    public Page<CategoryResponseDTO> toResponse(Page<Category> entity) {
        return entity.map(this::toResponse);
    }
    public abstract CategoryTranslation toEntity(CategoryTranslationDTO dto);

    @AfterMapping
    protected void mergeTranslations(
            Object dto,
            @MappingTarget Category entity
    ) {

        List<CategoryTranslationDTO> translations = null;

        if (dto instanceof CategoryUpdateDTO u) {
            translations = u.getTranslations();
        } else if (dto instanceof CategoryPatchDTO p) {
            translations = p.getTranslations();
        }

        if (translations == null) return;

        Map<String, CategoryTranslation> existing =
                entity.getTranslations()
                        .stream()
                        .collect(Collectors.toMap(
                                CategoryTranslation::getLang,
                                Function.identity()
                        ));

        Set<String> requestLangs = translations
                .stream()
                .map(CategoryTranslationDTO::getLang)
                .collect(Collectors.toSet());

        for (CategoryTranslationDTO tDto : translations) {

            CategoryTranslation tr = existing.get(tDto.getLang());

            if (tr != null) {
                if (tDto.getTitle() != null) tr.setTitle(tDto.getTitle());
                if (tDto.getSlug() != null) tr.setSlug(tDto.getSlug());
                if (tDto.getDescription() != null) tr.setDescription(tDto.getDescription());
            } else {
                CategoryTranslation newT = toEntity(tDto);
                newT.setCategory(entity);
                entity.getTranslations().add(newT);
            }
        }

        if (dto instanceof CategoryUpdateDTO) {
            entity.getTranslations()
                    .removeIf(t -> !requestLangs.contains(t.getLang()));
        }
    }
}
