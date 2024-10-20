package ir.isc.academy.multi_thread_processing.utility;

import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    public static List<String[]> readCSV(MultipartFile file, int threadNumber,
                                       int totalThreads) throws IOException {
        List<String[]> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            br.readLine();
            String line;
            int lineNumber = 0;
            String otherThanQuote = " [^\"] ";
            String quotedString = String.format(" \" %s* \" ", otherThanQuote);
            String regex = String.format("(?x) "+ // enable comments, ignore white spaces
                            ",                         "+ // match a comma
                            "(?=                       "+ // start positive look ahead
                            "  (?:                     "+ //   start non-capturing group 1
                            "    %s*                   "+ //     match 'otherThanQuote' zero or more times
                            "    %s                    "+ //     match 'quotedString'
                            "  )*                      "+ //   end group 1 and repeat it zero or more times
                            "  %s*                     "+ //   match 'otherThanQuote'
                            "  $                       "+ // match the end of the string
                            ")                         ", // stop positive look ahead
                    otherThanQuote, quotedString, otherThanQuote);
            while ((line = br.readLine()) != null) {
                if (lineNumber % totalThreads == threadNumber) {
                    records.add(line.split(regex, -1));
                }
                lineNumber++;
            }
        }
        return records;
    }
}



