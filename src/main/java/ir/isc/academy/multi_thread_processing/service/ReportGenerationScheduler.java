package ir.isc.academy.multi_thread_processing.service;

import ir.isc.academy.multi_thread_processing.controller.FileUploadController;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReportGenerationScheduler {

    final FileUploadController fileUploadController;
    final ReportGenerationService reportGenerationService;

    @Scheduled(fixedDelay = 5000)
    public void scheduleReportGeneration() {
        if (fileUploadController.isProcessingCompleted()) {
            reportGenerationService.generateReports();
            fileUploadController.setProcessingCompleted(false);
        }
    }
}
