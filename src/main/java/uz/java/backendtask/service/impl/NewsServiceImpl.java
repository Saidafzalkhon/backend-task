package uz.java.backendtask.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.backendtask.dto.*;
import uz.java.backendtask.entity.News;
import uz.java.backendtask.entity.NewsHistory;
import uz.java.backendtask.entity.NewsTranslation;
import uz.java.backendtask.enumeration.NewsStatus;
import uz.java.backendtask.exception.NewsException;
import uz.java.backendtask.mapper.NewsHistoryMapper;
import uz.java.backendtask.mapper.NewsMapper;
import uz.java.backendtask.repository.NewsHistoryRepository;
import uz.java.backendtask.repository.NewsRepository;
import uz.java.backendtask.service.*;
import uz.java.backendtask.specification.PageRequest;
import uz.java.backendtask.specification.SpecBuilder;
import uz.java.backendtask.utils.SlugValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NewsServiceImpl implements NewsService {

    private final NewsRepository repo;
    private final NewsHistoryRepository newsHistoryRepository;
    private final NewsMapper mapper;
    private final SlugValidator slugValidator;
    private final CategoryService categoryService;
    private final MediaService mediaService;
    private final TagService tagService;
    private final UserService userService;
    private final NewsHistoryMapper newsHistoryMapper;
    private final NewsDiffBuilder newsDiffBuilder;

    @Transactional
    @Override
    public NewsResponseDTO create(NewsCreateDTO dto) {

        slugValidator.validateCreate(dto.getTranslations());

        News news = mapper.toEntity(dto);
        news.setAuthor(userService.current());
        news.setCategory(categoryService.findById(dto.getCategoryId()));

        if (dto.getCoverMediaId() != null)
            news.setCoverMedia(mediaService.findById(dto.getCoverMediaId()));

        if (dto.getTagIds() != null)
            news.setTags(tagService.findAllByIds(dto.getTagIds()));

        mergeTranslations(news, dto.getTranslations());

        return mapper.toResponse(repo.save(news));
    }

    @Transactional
    @Override
    public NewsResponseDTO update(Long id, NewsUpdateDTO dto) {

        slugValidator.validateUpdate(id, dto.getTranslations());

        News news = findAndDeletedFalse(id);

        if (dto.getCoverMediaId() != null)
            news.setCoverMedia(mediaService.findById(dto.getCoverMediaId()));

        if (dto.getTagIds() != null)
            news.setTags(tagService.findAllByIds(dto.getTagIds()));
        NewsSnapshot before = NewsSnapshot.from(news);
        mapper.update(dto, news);
        mergeTranslations(news, dto.getTranslations());
        NewsSnapshot after = NewsSnapshot.from(news);

        saveHistory(
                news,
                before,
                after,
                newsDiffBuilder.diff(before, after)
        );
        return mapper.toResponse(news);
    }

    @Transactional
    @Override
    public NewsResponseDTO patch(Long id, NewsPatchDTO dto) {

        News news = findAndDeletedFalse(id);
        NewsSnapshot before = NewsSnapshot.from(news);

        mapper.patch(dto, news);

        if (dto.getCoverMediaId() != null)
            news.setCoverMedia(mediaService.findById(dto.getCoverMediaId()));

        if (dto.getTagIds() != null)
            news.setTags(tagService.findAllByIds(dto.getTagIds()));
        if (dto.getTranslations() != null) {
            slugValidator.validateUpdate(id, dto.getTranslations());
            mergeTranslations(news, dto.getTranslations());
        }

        NewsSnapshot after = NewsSnapshot.from(news);

        saveHistory(
                news,
                before,
                after,
                newsDiffBuilder.diff(before, after)
        );

        return mapper.toResponse(news);
    }

    @Override
    @Transactional
    public void deleteSoft(Long id) {

        News news = findAndDeletedFalse(id);
        NewsSnapshot before = NewsSnapshot.from(news);

        news.setIsDeleted(true);
        news.setDeletedAt(LocalDateTime.now());
        saveHistory(
                news,
                before,
                NewsSnapshot.from(news),
                Map.of("isDeleted", Map.of("old", false, "new", true))
        );

        repo.save(news);
    }

    @Override
    @Transactional
    public void deleteHard(Long id) {
        News news = find(id);
        List<NewsHistory> byNewsIdOrderByChangedAtDesc = newsHistoryRepository.findByNewsIdOrderByChangedAtDesc(id);
        newsHistoryRepository.deleteAll(byNewsIdOrderByChangedAtDesc);
        repo.delete(news);
    }

    @Override
    @Transactional
    public void restore(Long id) {
        News news = find(id);
        news.setIsDeleted(false);
        news.setDeletedAt(null);
        repo.save(news);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsHistoryResponseDTO> newsHistory(Long id) {
        return newsHistoryMapper.toResponse(newsHistoryRepository.findByNewsIdOrderByChangedAtDesc(id));
    }

    @Override
    @Transactional
    public NewsResponseDTO patch(Long id, NewsStatus status) {
        News news = findAndDeletedFalse(id);
        if (NewsStatus.DRAFT.equals(news.getStatus()) && !NewsStatus.REVIEW.equals(status))
            throw new NewsException("News status is stream not correct");
        if (NewsStatus.REVIEW.equals(news.getStatus()) && !NewsStatus.PUBLISHED.equals(status))
            throw new NewsException("News status is stream not correct");
        if (NewsStatus.PUBLISHED.equals(news.getStatus()) && !NewsStatus.UNPUBLISHED.equals(status))
            throw new NewsException("News status is stream not correct");
        if (NewsStatus.UNPUBLISHED.equals(news.getStatus()) && !NewsStatus.ARCHIVED.equals(status))
            throw new NewsException("News status is stream not correct");
        if (NewsStatus.ARCHIVED.equals(news.getStatus())) throw new NewsException("News status is stream not correct");

        NewsSnapshot before = NewsSnapshot.from(news);

        news.setStatus(status);

        NewsSnapshot after = NewsSnapshot.from(news);
        saveHistory(
                news,
                before,
                after,
                Map.of(
                        "status", Map.of(
                                "old", before.getStatus(),
                                "new", after.getStatus()
                        )
                )
        );
        return mapper.toResponse(repo.save(news));
    }

    private void mergeTranslations(
            News news,
            List<NewsTranslationDTO> list
    ) {

        Map<String, NewsTranslation> map =
                news.getTranslations().stream()
                        .collect(Collectors.toMap(
                                NewsTranslation::getLang,
                                t -> t
                        ));

        Set<String> langs = list.stream()
                .map(NewsTranslationDTO::getLang)
                .collect(Collectors.toSet());

        for (var dto : list) {
            NewsTranslation tr = map.get(dto.getLang());
            if (tr != null) {
                tr.setTitle(dto.getTitle());
                tr.setSlug(dto.getSlug());
                tr.setSummary(dto.getSummary());
                tr.setContent(dto.getContent());
                tr.setMetaTitle(dto.getMetaTitle());
                tr.setMetaDescription(dto.getMetaDescription());
            } else {
                NewsTranslation nt = mapper.toEntity(dto);
                nt.setNews(news);
                news.getTranslations().add(nt);
            }
        }

        news.getTranslations()
                .removeIf(t -> !langs.contains(t.getLang()));
    }

    @Transactional(readOnly = true)
    @Override
    public News find(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NewsException("News not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public News findBySlugAndLang(String slug, String lan) {
        return repo.findNewsBySlugAndLang(slug, lan)
                .orElseThrow(() -> new NewsException("News not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public News findAndDeletedFalse(Long id) {
        return repo.findFirstByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NewsException("News not found"));
    }

    @Override
    @Transactional
    @Cacheable(
            value = "list120s",
            key = "#slug + ':' + #lan"
    )
    public NewsResponseDTO getBySlugAndLang(String slug, String lan) {
        return mapper.toResponse(findBySlugAndLang(slug, lan));
    }

    @Override
    @Transactional
    public NewsResponseDTO getById(Long id) {
        return mapper.toResponse(find(id));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = "list60s",
            key = "#criteria.toString() + ':' + #request.pageNumber + ':' + #request.pageSize"
    )
    public Page<NewsResponseDTO> search(PageRequest request, NewsSearchCriteria criteria) {
        Specification<News> spec = new SpecBuilder<News>()
                .eq("id", criteria.getId())
                .joinEq("author", "id", criteria.getAuthorId())
                .joinEq("category", "id", criteria.getCategoryId())
                .joinEq("coverMedia", "id", criteria.getCoverMediaId())
                .eq("status", criteria.getStatus())
                .eq("isFeatured", criteria.getIsFeatured())
                .eq("isDeleted", criteria.getIsDeleted())
                .between("publishAt", criteria.getPublishAtFrom(), criteria.getPublishAtTo())
                .between("unpublishAt", criteria.getUnpublishAtFrom(), criteria.getUnpublishAtTo())
                .joinEq("translations", "lang", criteria.getLang())
                .joinEq("tags", "id", criteria.getTagId())
                .between("createdAt", criteria.getCreatedAtFrom(), criteria.getCreatedAtTo())
                .between("updatedAt", criteria.getUpdatedAtFrom(), criteria.getUpdatedAtTo())
                .build();

        Pageable page = org.springframework.data.domain.PageRequest.of(request.getPageNumber(), request.getPageSize(),
                Sort.by(request.getSortDirection(), request.getSortBy()));
        return mapper.toResponse(repo.findAll(spec, page));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = "list120s",
            key = "#criteria.toString() + ':' + #request.pageNumber + ':' + #request.pageSize"
    )
    public Page<NewsResponseDTO> searchPublic(PageRequest request, NewsPublicSearchCriteria criteria) {
        Specification<News> spec = new SpecBuilder<News>()
                .joinFts("translations", "searchFts", criteria.getSearch())
                .eq("status", "PUBLISHED")
                .joinEq("category", "id", criteria.getCategoryId())
                .joinEq("tags", "id", criteria.getTagId())
                .joinEq("translations", "lang", criteria.getLang())
                .between("createdAt", criteria.getCreatedAtFrom(), criteria.getCreatedAtTo())
                .eq("isDeleted", false)
                .build();

        Pageable page = org.springframework.data.domain.PageRequest.of(request.getPageNumber(), request.getPageSize(),
                Sort.by(request.getSortDirection(), request.getSortBy()));
        return mapper.toResponse(repo.findAll(spec, page));
    }

    private void saveHistory(
            News news,
            NewsSnapshot before,
            NewsSnapshot after,
            Map<String, Object> diff
    ) {
        if (diff.isEmpty()) return;

        NewsHistory h = new NewsHistory();
        h.setNews(news);
        h.setChangedBy(userService.current());
        h.setFromStatus(before.getStatus());
        h.setToStatus(after.getStatus());
        h.setDiffJson(diff);
        h.setChangedAt(LocalDateTime.now());

        newsHistoryRepository.save(h);
    }

}
