package project.moneymanager_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.moneymanager_api.dto.ExpenseDTO;
import project.moneymanager_api.entity.CategoryEntity;
import project.moneymanager_api.entity.ExpenseEntity;
import project.moneymanager_api.entity.ProfileEntity;
import project.moneymanager_api.repository.CategoryRepository;
import project.moneymanager_api.repository.ExpenseRepository;
import project.moneymanager_api.service.mapper.ExpenseMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final ProfileService profileService;

    public ExpenseDTO addExpense(ExpenseDTO expenseDTO){
        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntity category = categoryRepository.findById(expenseDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found."));

        ExpenseEntity expenseEntity = ExpenseMapper.getInstance().toEntity(expenseDTO, profile, category);
        expenseEntity = expenseRepository.save(expenseEntity);
        return ExpenseMapper.getInstance().toDto(expenseEntity);
    }

    public void deleteExpense(Long expenseId){
        ExpenseEntity expenseEntity = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found."));
        expenseRepository.delete(expenseEntity);
    }

    public List<ExpenseDTO> getExpenses() {
        ProfileEntity profile = profileService.getCurrentProfile();

        return expenseRepository.findByProfileIdOrderByDateDesc(profile.getId()).stream()
                .map(ExpenseMapper.getInstance()::toDto).toList();
    }

    public ExpenseDTO updateExpense(Long expenseId, ExpenseDTO dto) {
        ProfileEntity profile = profileService.getCurrentProfile();

        CategoryEntity existingCategory = categoryRepository.findByIdAndProfileId(dto.getCategoryId(), profile.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found."));

        ExpenseEntity expenseEntity = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found."));

        expenseEntity.setName(dto.getName());
        expenseEntity.setIcon(dto.getIcon());
        expenseEntity.setDate(dto.getDate() != null ? dto.getDate() : LocalDate.now());
        expenseEntity.setAmount(dto.getAmount());
        expenseEntity.setCategory(existingCategory);

        return ExpenseMapper.getInstance().toDto(expenseRepository.save(expenseEntity));
    }

    public BigDecimal getTotalExpenses() {
        ProfileEntity profile = profileService.getCurrentProfile();
        BigDecimal expenseTotal = expenseRepository.findTotalExpensesByProfileId(profile.getId());
        return expenseTotal != null ? expenseTotal : BigDecimal.ZERO;
    }
}