package ir.isc.academy.multi_thread_processing.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ReportDto {
    private String customerId;
    private String customerName;
    private String customerSurname;
    private String customerNationalId;
    private String accountNumber;
    private LocalDate accountOpenDate;
    private String accountBalance;

    public ReportDto(String customerId, String customerName, String customerSurname,
                     String customerNationalId, String accountNumber,
                     LocalDate accountOpenDate, BigDecimal accountBalance) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.customerNationalId = customerNationalId;
        this.accountNumber = accountNumber;
        this.accountOpenDate = accountOpenDate;
        this.accountBalance = accountBalance.toString();
    }
}
