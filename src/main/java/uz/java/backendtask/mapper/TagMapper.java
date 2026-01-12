package uz.java.backendtask.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;
import uz.java.backendtask.dto.TagCreateDTO;
import uz.java.backendtask.dto.TagResponseDTO;
import uz.java.backendtask.dto.TagUpdateDTO;
import uz.java.backendtask.dto.TagUpdatePatchDTO;
import uz.java.backendtask.entity.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {

    Tag toEntity(TagCreateDTO dto);

    TagResponseDTO toResponse(Tag tag);

    default Page<TagResponseDTO> toResponse(Page<Tag> tags) {
        return tags.map(this::toResponse);
    }


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    Tag updateEntity(TagUpdateDTO dto, @MappingTarget Tag tag);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Tag patchEntity(TagUpdatePatchDTO dto, @MappingTarget Tag tag);
}
