package project.moneymanaer_api.service.mapper;

import project.moneymanaer_api.dto.ExpenseDTO;
import project.moneymanaer_api.entity.CategoryEntity;
import project.moneymanaer_api.entity.ExpenseEntity;
import project.moneymanaer_api.entity.ProfileEntity;

import java.time.LocalDate;
import java.util.Date;

public class ExpenseMapper {

    private static ExpenseMapper instance;

    public static ExpenseMapper getInstance() {
        if (instance == null) {
            instance = new ExpenseMapper();
        }
        return instance;
    }

    public ExpenseEntity toEntity(ExpenseDTO dto, ProfileEntity profileEntity, CategoryEntity categoryEntity){
        return ExpenseEntity.builder()
                .name(dto.getName())
                .icon(dto.getIcon())
                .amount(dto.getAmount())
                .date(dto.getDate() == null ? LocalDate.now() : dto.getDate())
                .profile(profileEntity)
                .category(categoryEntity)
                .build();
    }

    public ExpenseDTO toDto(ExpenseEntity entity){
        return ExpenseDTO.builder()
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
