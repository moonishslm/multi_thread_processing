package ir.isc.academy.multi_thread_processing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ir.isc.academy.multi_thread_processing.model.ErrorLogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ErrorLogger {

    private final ObjectMapper objectMapper;
    List<ErrorLogDto> allErrors = new ArrayList<>();

    public void addErrors(List<ErrorLogDto> errors) {
        allErrors.addAll(errors);
    }

    public void logAllErrors() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File errorFile = new File("Errors_" + timestamp + ".json");

        try {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            objectMapper.writeValue(errorFile, allErrors);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}