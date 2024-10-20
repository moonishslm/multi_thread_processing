package ir.isc.academy.multi_thread_processing.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "customers")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String recordNumber;
    private String customerId;
    private String customerName;
    private String customerSurname;
    private String customerAddress;
    private String customerZipCode;
    private String customerNationalId;
    private LocalDate customerBirthDate;

}
