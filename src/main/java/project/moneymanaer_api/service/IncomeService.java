package project.moneymanaer_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.moneymanaer_api.dto.IncomeDTO;
import project.moneymanaer_api.entity.CategoryEntity;
import project.moneymanaer_api.entity.IncomeEntity;
import project.moneymanaer_api.entity.ProfileEntity;
import project.moneymanaer_api.repository.CategoryRepository;
import project.moneymanaer_api.repository.IncomeRepository;
import project.moneymanaer_api.service.mapper.IncomeMapper;

import java.awt.geom.Area;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final CategoryRepository categoryRepository;
    private final ProfileService profileService;

    public IncomeDTO addIncome(IncomeDTO incomeDTO){
        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntity category = categoryRepository.findById(incomeDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found."));

        IncomeEntity incomeEntity = IncomeMapper.getInstance().toEntity(incomeDTO, profile, category);
        incomeEntity = incomeRepository.save(incomeEntity);
        return IncomeMapper.getInstance().toDto(incomeEntity);
    }

    public void deleteIncome(Long expenseId){
        IncomeEntity incomeEntity = incomeRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Income not found."));
        incomeRepository.delete(incomeEntity);
    }

    public List<IncomeDTO> getIncomes() {
        ProfileEntity profile = profileService.getCurrentProfile();

        return incomeRepository.findByProfileIdOrderByDateDesc(profile.getId()).stream()
                .map(IncomeMapper.getInstance()::toDto).toList();
    }

    public IncomeDTO updateIncome(Long incomeId, IncomeDTO dto) {
        ProfileEntity profile = profileService.getCurrentProfile();

        CategoryEntity existingCategory = categoryRepository.findByIdAndProfileId(dto.getCategoryId(), profile.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found."));

        IncomeEntity incomeEntity = incomeRepository.findById(incomeId)
                .orElseThrow(() -> new RuntimeException("Income not found."));

        incomeEntity.setName(dto.getName());
        incomeEntity.setIcon(dto.getIcon());
        incomeEntity.setDate(dto.getDate() != null ? dto.getDate() : LocalDate.now());
        incomeEntity.setAmount(dto.getAmount());
        incomeEntity.setCategory(existingCategory);

        return IncomeMapper.getInstance().toDto(incomeRepository.save(incomeEntity));
    }

    public BigDecimal getTotalIncomes() {
        ProfileEntity profile = profileService.getCurrentProfile();
        BigDecimal totalIncomes = incomeRepository.findTotalIncomesByProfileId(profile.getId());
        return totalIncomes != null ? totalIncomes : BigDecimal.ZERO;
    }
}