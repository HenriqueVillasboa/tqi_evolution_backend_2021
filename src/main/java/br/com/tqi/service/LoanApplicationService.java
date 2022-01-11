package br.com.tqi.service;

import br.com.tqi.data.LoanApplicationDetailDTO;
import br.com.tqi.data.LoanApplicationListDTO;
import br.com.tqi.entity.ClientEntity;
import br.com.tqi.entity.LoanApplicationEntity;
import br.com.tqi.repository.ClientRepository;
import br.com.tqi.repository.LoanApplicationRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoanApplicationService {

    @Autowired private ClientRepository clientRepository;
    @Autowired private LoanApplicationRepository loanApplicationRepository;
    @Autowired private ModelMapper modelMapper;

    private ClientEntity getLoggedClient() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = auth.getName();
        return clientRepository.findOneByEmail(loggedUsername);
    }

    public List<LoanApplicationListDTO> getLoanByClientId() {
        List<LoanApplicationEntity> loansById = loanApplicationRepository.findByClientEntity(this.getLoggedClient());
        List<LoanApplicationListDTO> formattedLoans = modelMapper.map(loansById, new TypeToken<List<LoanApplicationListDTO>>() {}.getType());
        return formattedLoans;
    }

    public LoanApplicationDetailDTO getLoanById(@PathVariable UUID id) {
        LoanApplicationEntity loan = loanApplicationRepository.findById(id).get();
        if(this.getLoggedClient().getId() != loan.getClientEntity().getId()){
            throw new RuntimeException("Requisição não permitida.");
        }
        return modelMapper.map(loan, LoanApplicationDetailDTO.class);
    }

    public LoanApplicationEntity createLoanApplication(@RequestBody LoanApplicationEntity loan) {
        if (loan.getAmountInstallments() > 60){
            throw new RuntimeException("O máximo de parcelas deve ser 60.");
        }
        LocalDate firstDateInstallment = loan.getFirstInstallmentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        LocalDate limitDate = currentDate.plusMonths(3);
        if (firstDateInstallment.isAfter(limitDate)){
            throw new RuntimeException("Data da primeira parcela deve ser no máximo 3 meses após o dia atual.");
        }
        loan.setClientEntity(this.getLoggedClient());
        LoanApplicationEntity createdLoan = loanApplicationRepository.save(loan);
        return createdLoan;
    }
}
