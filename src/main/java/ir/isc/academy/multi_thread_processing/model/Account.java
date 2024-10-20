package ir.isc.academy.multi_thread_processing.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String recordNumber;
    private String  accountNumber;
    private String  accountType;
    private String accountCustomerId;
    private BigDecimal accountLimit;
    private LocalDate accountOpenDate;
    private BigDecimal accountBalance;

}
