package br.com.tqi.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationDetailDTO {

    private UUID loanId;
    private double value;
    private Integer amountInstallments;
    private Date firstInstallmentDate;
    private String clientEmail;
    private double clientIncome;
}
