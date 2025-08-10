package project.moneymanaer_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.moneymanaer_api.dto.ExpenseDTO;
import project.moneymanaer_api.dto.IncomeDTO;
import project.moneymanaer_api.dto.RecentTransactionDTO;
import project.moneymanaer_api.entity.ProfileEntity;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Stream.concat;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProfileService profileService;
    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    public Map<String, Object> getDashboardData(){
        ProfileEntity profile = profileService.getCurrentProfile("Nelio");
        Map<String, Object> dashboardData = new LinkedHashMap<>();

        List<IncomeDTO> lastestIncomes = incomeService.getIncomes();
        List<ExpenseDTO> lastestExpenses = expenseService.getExpenses();

        List<RecentTransactionDTO> recentTransactionDTOS = concat(
                lastestIncomes.stream().map(incomeDTO -> RecentTransactionDTO.builder()
                        .id(incomeDTO.getId())
                        .profileId(profile.getId())
                        .icon(incomeDTO.getIcon())
                        .name(incomeDTO.getName())
                        .amount(incomeDTO.getAmount())
                        .date(incomeDTO.getDate())
                        .createdAt(incomeDTO.getCreatedAt())
                        .updatedAt(incomeDTO.getUpdatedAt())
                        .type("income")
                        .build()),
                lastestExpenses.stream().map(expenseDTO -> RecentTransactionDTO.builder()
                        .id(expenseDTO.getId())
                        .profileId(profile.getId())
                        .icon(expenseDTO.getIcon())
                        .amount(expenseDTO.getAmount())
                        .name(expenseDTO.getName())
                        .date(expenseDTO.getDate())
                        .createdAt(expenseDTO.getCreatedAt())
                        .updatedAt(expenseDTO.getUpdatedAt())
                        .type("expense")
                        .build()))
                .sorted(Comparator.comparing(RecentTransactionDTO::getDate)
                        .thenComparing(dto -> Optional.ofNullable(dto.getCreatedAt())
                                .orElse(LocalDateTime.MIN), Comparator.reverseOrder()))
                .toList();

        dashboardData.put("totalBalance", incomeService.getTotalIncomes().subtract(expenseService.getTotalExpenses()));
        dashboardData.put("totalIncomes", incomeService.getTotalIncomes());
        dashboardData.put("totalExpenses", expenseService.getTotalExpenses());
        dashboardData.put("lastIncomes", lastestIncomes);
        dashboardData.put("lastExpenses", lastestExpenses);
        dashboardData.put("recentTransactions", recentTransactionDTOS);

        return dashboardData;
    }
}

