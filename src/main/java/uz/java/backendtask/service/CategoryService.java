package uz.java.backendtask.service;

import org.springframework.data.domain.Page;
import uz.java.backendtask.dto.CategoryCreateDTO;
import uz.java.backendtask.dto.CategoryPatchDTO;
import uz.java.backendtask.dto.CategoryResponseDTO;
import uz.java.backendtask.dto.CategoryUpdateDTO;
import uz.java.backendtask.entity.Category;
import uz.java.backendtask.specification.PageRequest;

import java.util.List;

public interface CategoryService {

    CategoryResponseDTO create(CategoryCreateDTO dto);

    CategoryResponseDTO update(Long id, CategoryUpdateDTO dto);

    CategoryResponseDTO patch(Long id, CategoryPatchDTO dto);

    CategoryResponseDTO getById(Long id);

    Category findById(Long id);

    void delete(Long id);

    List<CategoryResponseDTO> getList(
            Long parentId,
            Boolean isActive
    );

    Page<CategoryResponseDTO> searchPublic(PageRequest request, String lang);
}
