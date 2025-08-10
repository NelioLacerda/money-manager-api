package project.moneymanaer_api.service.mapper;

import project.moneymanaer_api.dto.IncomeDTO;
import project.moneymanaer_api.entity.CategoryEntity;
import project.moneymanaer_api.entity.IncomeEntity;
import project.moneymanaer_api.entity.ProfileEntity;

import java.time.LocalDate;

public class IncomeMapper {
    private static IncomeMapper instance;

    public static IncomeMapper getInstance() {
        if (instance == null) {
            instance = new IncomeMapper();
        }
        return instance;
    }

    public IncomeEntity toEntity(IncomeDTO dto, ProfileEntity profileEntity, CategoryEntity categoryEntity){
        return IncomeEntity.builder()
                .name(dto.getName())
                .icon(dto.getIcon())
                .amount(dto.getAmount())
                .date(dto.getDate() == null ? LocalDate.now() : dto.getDate())
                .profile(profileEntity)
                .category(categoryEntity)
                .build();
    }

    public IncomeDTO toDto(IncomeEntity entity){
        return IncomeDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .icon(entity.getIcon())
                .amount(entity.getAmount())
                .date(entity.getDate())
                .categoryId(entity.getCategory() != null ? entity.getCategory().getId() : null)
                .categoryName(entity.getCategory() != null ? entity.getCategory().getName() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}