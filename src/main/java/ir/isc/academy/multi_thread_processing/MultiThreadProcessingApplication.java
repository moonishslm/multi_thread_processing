package ir.isc.academy.multi_thread_processing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MultiThreadProcessingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiThreadProcessingApplication.class, args);
    }

}
