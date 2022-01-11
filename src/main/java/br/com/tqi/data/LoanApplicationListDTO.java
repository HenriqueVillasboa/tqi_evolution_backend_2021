package br.com.tqi.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationListDTO {

    private UUID id;
    private double value;
    private Integer amountInstallments;
}
