package ir.isc.academy.multi_thread_processing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ir.isc.academy.multi_thread_processing.model.ReportDto;
import ir.isc.academy.multi_thread_processing.repository.ReportRepo;
import ir.isc.academy.multi_thread_processing.utility.CryptoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportGenerationService {
    private final ReportRepo reportRepo;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public void generateReports() {
        List<ReportDto> results = fetchDataFromDatabase();
        saveToJsonFile(results);
        saveToXmlFile(results);
    }

    private List<ReportDto> fetchDataFromDatabase() {
        List<ReportDto> reports = reportRepo.fetchReports();
        reports.forEach(report -> {
            try {
                report.setAccountNumber(CryptoUtils.encrypt(report.getAccountNumber()));
                report.setAccountBalance(CryptoUtils.encrypt(report.getAccountBalance()));
            } catch (Exception e) {
                throw new RuntimeException("Encryption error", e);
            }
        });
        return reports;
    }

    private void saveToJsonFile(List<ReportDto> data) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File jsonFile = new File("report_" + timestamp + ".json");

        try {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            objectMapper.writeValue(jsonFile, data);
        } catch (IOException e) {
            throw new RuntimeException("Error saving JSON file", e);
        }
    }

    private void saveToXmlFile(List<ReportDto> data) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File xmlFile = new File("report_" + timestamp + ".xml");

        try {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.registerModule(new JavaTimeModule());
            xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            xmlMapper.writeValue(xmlFile, data);
        } catch (IOException e) {
            throw new RuntimeException("Error saving XML file", e);
        }
    }
}
