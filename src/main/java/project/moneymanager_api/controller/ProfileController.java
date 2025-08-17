package project.moneymanager_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.moneymanager_api.dto.*;
import project.moneymanager_api.service.CategoryService;
import project.moneymanager_api.service.ExpenseService;
import project.moneymanager_api.service.IncomeService;
import project.moneymanager_api.service.ProfileService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final ExpenseService expenseService;
    private final IncomeService incomeService;
    private final CategoryService categoryService;

    @PostMapping("/register")
    public ResponseEntity<ProfileDTO> register(@RequestBody ProfileDTO profileDTO){
        ProfileDTO newProfile = profileService.register(profileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProfile);
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateProfile(@RequestParam String token) {
        if (profileService.activateProfile(token)) {
            return ResponseEntity.ok("Profile activated successfully.");
        }
        return ResponseEntity.badRequest().body("Invalid activation token.");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthDTO authDTO){
        try {
            if (!profileService.isProfileActive(authDTO.getEmail()))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Profile not activated."));

            Map<String, Object> response = profileService.login(authDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileDTO> getPublicProfile() {
        return ResponseEntity.ok(profileService.getCurrentProfileDTO(null));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteProfile() {
        List<IncomeDTO> incomes = incomeService.getIncomes();
        for (IncomeDTO income : incomes){
            incomeService.deleteIncome(income.getId());
        }
        List<ExpenseDTO> expenses = expenseService.getExpenses();
        for (ExpenseDTO expense : expenses){
            expenseService.deleteExpense(expense.getId());
        }
        List<CategoryDTO> categories = categoryService.getUserCategories();
        for (CategoryDTO category : categories){
            categoryService.deleteCategory(category.getId());
        }
        profileService.deleteCurrentProfile();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/photo")
    public ResponseEntity<ProfileDTO> updateProfilePhoto(@RequestBody String profileImageUrl){
        return ResponseEntity.ok().body(profileService.updateProfilePhoto(profileImageUrl));
    }
}