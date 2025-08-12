package project.moneymanager_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.moneymanager_api.dto.FilterDTO;
import project.moneymanager_api.service.ExpenseService;
import project.moneymanager_api.service.IncomeService;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/filter")
public class FilterController {

    private final ExpenseService expenseService;
    private final IncomeService incomeService;

    @PostMapping
    public ResponseEntity<?> filterTransactions(@RequestBody FilterDTO filter) {
        LocalDate startDate = filter.getStartDate() != null ? filter.getStartDate() : LocalDate.MIN;
        LocalDate endDate = filter.getEndDate() != null ? filter.getEndDate() : LocalDate.now();
        String keyword = filter.getKeyword() != null ? filter.getKeyword() : "";
        String sortField = filter.getSortField() != null ? filter.getSortField() : "date";
        Sort.Direction sortOrder = "desc".equalsIgnoreCase(filter.getSortOrder()) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortOrder, sortField);

        if (filter.getType().equals("income")) {
            return ResponseEntity.ok(incomeService.filterIncomes(startDate, endDate, keyword, sort));
        } else if (filter.getType().equals("expense")) {
            return ResponseEntity.ok(expenseService.filterExpenses(startDate, endDate, keyword, sort));
        }

        return ResponseEntity.badRequest().body("Invalid filter type.");
    }
}
