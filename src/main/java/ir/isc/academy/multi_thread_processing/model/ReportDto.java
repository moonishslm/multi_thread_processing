package ir.isc.academy.multi_thread_processing.model;

import lombok.Data;
import lombok.NoArgsConstructor;

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
}
