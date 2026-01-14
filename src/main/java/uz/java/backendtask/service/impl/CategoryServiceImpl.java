package uz.java.backendtask.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.backendtask.dto.CategoryCreateDTO;
import uz.java.backendtask.dto.CategoryPatchDTO;
import uz.java.backendtask.dto.CategoryResponseDTO;
import uz.java.backendtask.dto.CategoryUpdateDTO;
import uz.java.backendtask.entity.Category;
import uz.java.backendtask.entity.CategoryTranslation;
import uz.java.backendtask.exception.CategoryException;
import uz.java.backendtask.mapper.CategoryMapper;
import uz.java.backendtask.repository.CategoryRepository;
import uz.java.backendtask.service.CategoryService;
import uz.java.backendtask.specification.PageRequest;
import uz.java.backendtask.specification.SpecBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    @Transactional
    public CategoryResponseDTO create(CategoryCreateDTO dto) {

        Category category = mapper.toEntity(dto);

        if (dto.getParentId() != null) {
            category.setParent(findById(dto.getParentId()));
        }

        if (dto.getTranslations() != null) {
            dto.getTranslations().forEach(t -> {
                CategoryTranslation ct = mapper.toEntity(t);
                ct.setCategory(category);
                category.getTranslations().add(ct);
            });
        }

        return mapper.toResponse(repository.save(category));
    }

    @Override
    @Transactional
    public CategoryResponseDTO update(Long id, CategoryUpdateDTO dto) {

        Category category = findById(id);

        if (dto.getParentId() != null) {
            category.setParent(findById(dto.getParentId()));
        }

        mapper.updateEntity(dto, category);

        return mapper.toResponse(category);
    }

    @Override
    @Transactional
    public CategoryResponseDTO patch(Long id, CategoryPatchDTO dto) {

        Category category = findById(id);

        if (dto.getParentId() != null) {
            category.setParent(findById(dto.getParentId()));
        }

        mapper.patchEntity(dto, category);

        return mapper.toResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDTO getById(Long id) {
        return mapper.toResponse(findById(id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new CategoryException("Category not found: " + id)
                );
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = "list60s",
            key = "#parentId + ':' + #isActive"
    )
    public List<CategoryResponseDTO> getList(
            Long parentId,
            Boolean isActive
    ) {

        List<Category> categories;

        if (parentId != null) {
            categories = repository.findAllByParentId(parentId);
        } else if (isActive != null) {
            categories = repository.findAllByIsActive(isActive);
        } else {
            categories = repository.findAll();
        }

        return categories.stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = "list120s",
            key = "#lang"
    )
    public Page<CategoryResponseDTO> searchPublic(PageRequest request, String lang) {
        Specification<Category> spec = new SpecBuilder<Category>()
                .joinEq("translations", "lang", lang)
                .eq("isActive", true)
                .build();

        Pageable page = org.springframework.data.domain.PageRequest.of(request.getPageNumber(), request.getPageSize(),
                Sort.by(request.getSortDirection(), request.getSortBy()));
        return mapper.toResponse(repository.findAll(spec, page));
    }

}
