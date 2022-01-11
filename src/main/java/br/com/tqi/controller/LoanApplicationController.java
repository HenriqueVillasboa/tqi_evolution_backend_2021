package br.com.tqi.controller;

import br.com.tqi.data.LoanApplicationDetailDTO;
import br.com.tqi.data.LoanApplicationListDTO;
import br.com.tqi.entity.ClientEntity;
import br.com.tqi.entity.LoanApplicationEntity;
import br.com.tqi.service.ClientService;
import br.com.tqi.service.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/loanApplication")
public class LoanApplicationController {

    @Autowired private LoanApplicationService loanService;
    @Autowired private ClientService clientService;

    @GetMapping
    public List<LoanApplicationListDTO> getLoanByClientId() {
        return loanService.getLoanByClientId();
    }

    @GetMapping(value = "/{id}")
    public LoanApplicationDetailDTO getLoanById(@PathVariable UUID id) {
        return loanService.getLoanById(id);
    }

    @PostMapping
    public LoanApplicationEntity createLoanApplication(@RequestBody LoanApplicationEntity loan) {
        return loanService.createLoanApplication(loan);
    }
}
