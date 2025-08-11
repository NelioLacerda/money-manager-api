package project.moneymanager_api.service.mapper;

import project.moneymanager_api.constants.CategoryType;
import project.moneymanager_api.dto.CategoryDTO;
import project.moneymanager_api.entity.CategoryEntity;
import project.moneymanager_api.entity.ProfileEntity;

public class CategoryMapper {

    private static CategoryMapper instance;

    public static CategoryMapper getInstance() {
        if (instance == null) {
            instance = new CategoryMapper();
        }
        return instance;
    }

    public CategoryEntity toEntity(CategoryDTO dto, ProfileEntity profileEntity){
        return CategoryEntity.builder()
                .name(dto.getName())
                .type(CategoryType.fromString(dto.getType()))
                .icon(dto.getIcon())
                .profile(profileEntity)
                .build();
    }

    public CategoryDTO toDto(CategoryEntity entity){
        return CategoryDTO.builder()
                .id(entity.getId())
                .profileId(entity.getProfile() != null ? entity.getProfile().getId() : null)
                .name(entity.getName())
                .icon(entity.getIcon())
                .type(entity.getType().getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
