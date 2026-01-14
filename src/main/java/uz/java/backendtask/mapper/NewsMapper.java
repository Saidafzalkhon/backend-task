package uz.java.backendtask.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import uz.java.backendtask.dto.*;
import uz.java.backendtask.entity.AdsCreative;
import uz.java.backendtask.entity.News;
import uz.java.backendtask.entity.NewsTranslation;

@Mapper(componentModel = "spring", imports = {java.util.stream.Collectors.class})
public abstract class NewsMapper {


    @Autowired
    protected TagMapper tagMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "coverMedia", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "translations", ignore = true)
    @Mapping(target = "status", constant = "DRAFT")
    @Mapping(target = "isDeleted", constant = "false")
    public abstract News toEntity(NewsCreateDTO dto);

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "coverMedia", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "translations", ignore = true)
    public abstract void update(
            NewsUpdateDTO dto,
            @MappingTarget News entity
    );

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "coverMedia", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "translations", ignore = true)
    public abstract void patch(
            NewsPatchDTO dto,
            @MappingTarget News entity
    );

    public abstract NewsTranslation toEntity(NewsTranslationDTO dto);

    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "coverMedia.id", target = "coverMediaId")
    @Mapping(
            target = "tags",
            expression = "java(entity.getTags().stream().map(value-> tagMapper.toResponse(value)).collect(Collectors.toSet()))"
    )
    public abstract NewsResponseDTO toResponse(News entity);


    public Page<NewsResponseDTO> toResponse(Page<News> news) {
        return news.map(this::toResponse);
    }

    public abstract NewsTranslationResponseDTO toResponse(NewsTranslation entity);


}
