package uz.java.backendtask.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import uz.java.backendtask.dto.NewsHistoryResponseDTO;
import uz.java.backendtask.entity.NewsHistory;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class NewsHistoryMapper {

    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected NewsMapper newsMapper;

    @Mapping(
            target = "news",
            expression = "java(newsMapper.toResponse(entity.getNews()))"
    )
    @Mapping(
            target = "changedBy",
            expression = "java(userMapper.toResponse(entity.getChangedBy()))"
    )
    public abstract NewsHistoryResponseDTO toResponse(NewsHistory entity);

    public List<NewsHistoryResponseDTO> toResponse(List<NewsHistory> entities){
        return entities.stream().map(this::toResponse).toList();
    }
}
