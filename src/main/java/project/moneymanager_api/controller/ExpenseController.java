package project.moneymanager_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.moneymanager_api.dto.ExpenseDTO;
import project.moneymanager_api.service.ExpenseService;

import java.util.List;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseDTO> addExpense(@RequestBody ExpenseDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.addExpense(dto));
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseDTO> updateExpense(@PathVariable Long expenseId, @RequestBody ExpenseDTO dto){
        return ResponseEntity.ok(expenseService.updateExpense(expenseId, dto));
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId){
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getExpenses(){
        return ResponseEntity.ok(expenseService.getExpenses());
    }
}