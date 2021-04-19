package com.assignment2.report;
import com.assignment2.books.BookService;
import com.opencsv.CSVWriter;
import com.assignment2.books.model.dto.BookDTO;
import org.springframework.stereotype.Service;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVReportService implements ReportService {

    private final BookService bookService;

    public CSVReportService(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String export() throws IOException {
        List<String[]> csvData = createCSVData(bookService.findAll());
        try (CSVWriter writer = new CSVWriter(new FileWriter("report.csv"))) {
            writer.writeAll(csvData);
        }
        return "I am a CSV reporter.";
    }

    private static List<String[]> createCSVData(List<BookDTO> books) {
        String[] header = {"Book Id", "Title", "Author", "Price"};
        List<String[]> list = new ArrayList<>();
        list.add(header);
        for (BookDTO book: books) {
            if (book.getQuantity() == 0) {
                String[] data = { book.getId().toString(), book.getTitle(),book.getAuthor(), book.getPrice().toString()};
                list.add(data);
            }
        }
        return list;
    }

    @Override
    public ReportType getType() {
        return ReportType.CSV;
    }
}
