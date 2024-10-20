package ir.isc.academy.multi_thread_processing.model;

import lombok.Getter;

@Getter
public enum ErrorCode {
    BALANCE_EXCEEDS_LIMIT("1", "Account balance exceeds account limit"),
    INVALID_ACCOUNT_TYPE("2", "Invalid account type"),
    INVALID_ACCOUNT_NUMBER("3", "Account number must be 22 digits"),
    INVALID_BIRTH_DATE("4", "Customer birth date must be after 1995"),
    INVALID_NATIONAL_ID("5", "National ID must be 10 digits"),
    NULL_FIELD("6", "NULL field");

    private final String code;
    private final String description;

    ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

}