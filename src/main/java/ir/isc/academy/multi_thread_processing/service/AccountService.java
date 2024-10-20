package ir.isc.academy.multi_thread_processing.service;

import ir.isc.academy.multi_thread_processing.model.ErrorLogDto;
import ir.isc.academy.multi_thread_processing.model.Account;
import ir.isc.academy.multi_thread_processing.model.ErrorCode;
import ir.isc.academy.multi_thread_processing.repository.AccountRepo;
import ir.isc.academy.multi_thread_processing.utility.CryptoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepo accountRepo;
    private final ValidationService validationService;
    private final ErrorLogger errorLogger;

    public void processAndSaveAccounts(List<String[]> records) {
        List<ErrorLogDto> accountErrors = new ArrayList<>();

        for (String[] record : records) {
            try {
                Account account = createAccount(record);
                if (validationService.validateAccount(account)) {
                    accountRepo.save(account);
                } else {
                    String errorMessage = getValidationErrorMessage(account);
                    ErrorCode errorCode = getErrorCode(errorMessage);
                    accountErrors.add(new ErrorLogDto(
                            "Account",
                            account.getRecordNumber(),
                            errorCode.getCode(),
                            "Validation",
                            errorCode.getDescription()
                    ));
                }
            } catch (Exception e) {
                accountErrors.add(new ErrorLogDto(
                        "Account",
                        record[0],
                        ErrorCode.NULL_FIELD.getCode(),
                        "Processing Error",
                        e.getMessage()
                ));
            }
        }
        if (!accountErrors.isEmpty()) {
            errorLogger.addErrors(accountErrors);
        }
    }

    private String getValidationErrorMessage(Account account) {
        if (account.getAccountBalance().compareTo(account.getAccountLimit()) > 0) {
            return ErrorCode.BALANCE_EXCEEDS_LIMIT.getDescription();
        }
        if (!isValidAccountType(account.getAccountType())) {
            return ErrorCode.INVALID_ACCOUNT_TYPE.getDescription();
        }
        if (account.getAccountNumber().length() != 22) {
            return ErrorCode.INVALID_ACCOUNT_NUMBER.getDescription();
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

    private boolean isValidAccountType(String type) {
        return Arrays.asList("1", "2", "3").contains(type);
    }


    private Account createAccount(String[] record) throws Exception {
        return new Account(
                null, //id
                record[0], //recordNumber
                CryptoUtils.decrypt(record[1]), //accountNumber
                record[2], //accountType
                record[3], //accountCustomerId
                new BigDecimal(record[4]), //accountLimit
                LocalDate.parse(record[5]), //accountOpenDate
                new BigDecimal(CryptoUtils.decrypt(record[6])) //accountBalance
        );
    }



}
