package ir.isc.academy.multi_thread_processing.service;

import ir.isc.academy.multi_thread_processing.utility.CSVReader;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class FileProcessingService {

    @Async
    public void processFile(MultipartFile file, Consumer<List<String[]>> processor) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            final int threadNumber = i;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    List<String[]> records = CSVReader.readCSV(file, threadNumber, 5);
                    processor.accept(records);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, executor);
            futures.add(future);
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();
    }
}
