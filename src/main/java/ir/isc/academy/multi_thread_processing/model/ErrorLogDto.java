package ir.isc.academy.multi_thread_processing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorLogDto {
    private String fileName;
    private String recordNumber;
    private String errorCode;
    private String errorClassificationName;
    private String errorDescription;
    private LocalDateTime errorDate;

    public ErrorLogDto(String fileName, String recordNumber, String errorCode,
                       String errorClassificationName, String errorDescription) {
        this.fileName = fileName;
        this.recordNumber = recordNumber;
        this.errorCode = errorCode;
        this.errorClassificationName = errorClassificationName;
        this.errorDescription = errorDescription;
        this.errorDate = LocalDateTime.now();
    }
}