package ir.isc.academy.multi_thread_processing.service;

import ir.isc.academy.multi_thread_processing.model.Customer;
import ir.isc.academy.multi_thread_processing.model.ErrorCode;
import ir.isc.academy.multi_thread_processing.model.ErrorLogDto;
import ir.isc.academy.multi_thread_processing.repository.CustomerRepo;
import ir.isc.academy.multi_thread_processing.utility.CryptoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final ValidationService validationService;
    private final ErrorLogger errorLogger;

    public void processAndSaveCustomers(List<String[]> records) {
        List<ErrorLogDto> customerErrors = new ArrayList<>();

        for (String[] record : records) {
            try {
                Customer customer = createCustomer(record);
                if (validationService.validateCustomer(customer)) {
                    customerRepo.save(customer);
                } else {
                    String errorMessage = getValidationErrorMessage(customer);
                    ErrorCode errorCode = getErrorCode(errorMessage);
                    customerErrors.add(new ErrorLogDto(
                            "Customer",
                            customer.getRecordNumber(),
                            errorCode.getCode(),
                            "Validation",
                            errorCode.getDescription()
                    ));
                }
            } catch (Exception e) {
                customerErrors.add(new ErrorLogDto(
                        "Customer",
                        record[0],
                        ErrorCode.NULL_FIELD.getCode(),
                        "Processing Error",
                        e.getMessage()
                ));
            }
        }
        if (!customerErrors.isEmpty()) {
            errorLogger.addErrors(customerErrors);
        }
    }

    private String getValidationErrorMessage(Customer customer) {
        if (customer.getCustomerBirthDate().isBefore(LocalDate.of(1995, 1, 1))) {
            return ErrorCode.INVALID_BIRTH_DATE.getDescription();
        }
        if (customer.getCustomerNationalId().length() != 10) {
            return ErrorCode.INVALID_NATIONAL_ID.getDescription();
        }
        return null;
    }

    private ErrorCode getErrorCode(String errorMessage) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (errorCode.getDescription().equals(errorMessage)) {
                return errorCode;
            }
        }
        return ErrorCode.NULL_FIELD;
    }

    private Customer createCustomer(String[] record) throws Exception {
        return new Customer(
                null, //id
                record[0], //recordNumber
                record[1], //customerId
                CryptoUtils.decrypt(record[2]), //customerName
                CryptoUtils.decrypt(record[3]), //customerSurname
                record[4].replace("\"", ""), //customerAddress
                record[5], //customerZipCode
                CryptoUtils.decrypt(record[6]), //customerNationalId
                LocalDate.parse(record[7]) //customerBirthDate
        );
    }


}
