package uz.java.backendtask.service;

import org.springframework.data.domain.Page;
import uz.java.backendtask.dto.*;
import uz.java.backendtask.entity.News;
import uz.java.backendtask.enumeration.NewsStatus;
import uz.java.backendtask.specification.PageRequest;

import java.util.List;

public interface NewsService {


    NewsResponseDTO create(NewsCreateDTO dto);

    NewsResponseDTO update(Long id, NewsUpdateDTO dto);

    NewsResponseDTO patch(Long id, NewsPatchDTO dto);

    NewsResponseDTO patch(Long id, NewsStatus status);

    List<NewsHistoryResponseDTO> newsHistory(Long id);

    void deleteSoft(Long id);

    void deleteHard(Long id);

    void restore(Long id);

    News find(Long id);
    News findBySlugAndLang(String slug,String lan);
    News findAndDeletedFalse(Long id);

    NewsResponseDTO getById(Long id);

    NewsResponseDTO getBySlugAndLang(String slug, String lan);

    Page<NewsResponseDTO> search(PageRequest request, NewsSearchCriteria criteria);

    Page<NewsResponseDTO> searchPublic(PageRequest request, NewsPublicSearchCriteria criteria);
}
