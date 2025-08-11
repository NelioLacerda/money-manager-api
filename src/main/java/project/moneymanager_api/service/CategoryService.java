package project.moneymanager_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.moneymanager_api.constants.CategoryType;
import project.moneymanager_api.dto.CategoryDTO;
import project.moneymanager_api.entity.CategoryEntity;
import project.moneymanager_api.entity.ProfileEntity;
import project.moneymanager_api.repository.CategoryRepository;
import project.moneymanager_api.service.mapper.CategoryMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final ProfileService profileService;

    public CategoryDTO addCategory(CategoryDTO categoryDTO){
        ProfileEntity profileEntity = profileService.getCurrentProfile();
        if (categoryRepository.existsByNameAndProfileId(categoryDTO.getName(), profileEntity.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category already exists.");
        }

        CategoryEntity categoryEntity = CategoryMapper.getInstance().toEntity(categoryDTO, profileEntity);
        categoryEntity = categoryRepository.save(categoryEntity);
        return CategoryMapper.getInstance().toDto(categoryEntity);
    }

    public List<CategoryDTO> getUserCategories(){
        ProfileEntity profileEntity = profileService.getCurrentProfile();

        return categoryRepository.findByProfileId(profileEntity.getId()).stream()
                .map(CategoryMapper.getInstance()::toDto).toList();
    }

    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        ProfileEntity profileEntity = profileService.getCurrentProfile();
        CategoryEntity existingCategory = categoryRepository.findByIdAndProfileId(categoryId, profileEntity.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found."));

        existingCategory.setName(categoryDTO.getName());
        existingCategory.setIcon(categoryDTO.getIcon());
        existingCategory.setType(CategoryType.fromString(categoryDTO.getType()));
        existingCategory = categoryRepository.save(existingCategory);
        return CategoryMapper.getInstance().toDto(existingCategory);
    }

    //todo: precisa ser mudado
    public void deleteCategory(Long categoryId){
        ProfileEntity profileEntity = profileService.getCurrentProfile();

        CategoryEntity existingCategory = categoryRepository.findByIdAndProfileId(categoryId, profileEntity.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found."));

        categoryRepository.delete(existingCategory);
    }
}
