package ir.isc.academy.multi_thread_processing.controller;

import ir.isc.academy.multi_thread_processing.service.AccountService;
import ir.isc.academy.multi_thread_processing.service.CustomerService;
import ir.isc.academy.multi_thread_processing.service.ErrorLogger;
import ir.isc.academy.multi_thread_processing.service.FileProcessingService;
import ir.isc.academy.multi_thread_processing.utility.CSVReader;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FileUploadController {

    final AccountService accountService;
    final CustomerService customerService;
    final ErrorLogger errorLogger;
    final FileProcessingService fileProcessingService;
    @Getter
    @Setter
    private volatile boolean processingCompleted = false;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFiles(
            @RequestParam("accountFile") MultipartFile accountFile,
            @RequestParam("customerFile") MultipartFile customerFile) {

        CompletableFuture<Void> accountFuture = CompletableFuture.runAsync(() ->
                fileProcessingService.processFile(accountFile, accountService::processAndSaveAccounts));

        CompletableFuture<Void> customerFuture = CompletableFuture.runAsync(() ->
                fileProcessingService.processFile(customerFile, customerService::processAndSaveCustomers));

        CompletableFuture.allOf(accountFuture, customerFuture)
                .thenRun(() -> {
                    errorLogger.logAllErrors();
                    processingCompleted = true;
                })
                .join();
        return ResponseEntity.ok("Files processed successfully");
    }

}
