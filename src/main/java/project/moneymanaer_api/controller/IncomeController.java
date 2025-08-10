package project.moneymanaer_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.moneymanaer_api.dto.IncomeDTO;
import project.moneymanaer_api.service.IncomeService;

import java.util.List;

@RestController
@RequestMapping("/incomes")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    @PostMapping
    public ResponseEntity<IncomeDTO> addIncome(@RequestBody IncomeDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(incomeService.addIncome(dto));
    }

    @PutMapping("/{incomeId}")
    public ResponseEntity<IncomeDTO> updateIncome(@PathVariable Long incomeId, @RequestBody IncomeDTO dto){
        return ResponseEntity.ok(incomeService.updateIncome(incomeId, dto));
    }

    @DeleteMapping("/{incomeId}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long incomeId){
        incomeService.deleteIncome(incomeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<IncomeDTO>> getIncomes(){
        return ResponseEntity.ok(incomeService.getIncomes());
    }
}