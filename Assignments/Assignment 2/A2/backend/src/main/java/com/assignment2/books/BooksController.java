package com.assignment2.books;

import com.assignment2.books.model.dto.BookDTO;
import com.assignment2.report.ReportServiceFactory;
import com.assignment2.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.assignment2.UrlMapping.*;

@RestController
@RequestMapping(BOOK_STORE)
@RequiredArgsConstructor
public class BooksController {

    private final ReportServiceFactory reportServiceFactory;
    private final BookService bookService;

    @GetMapping
    public List<BookDTO> allBooks() {
        return bookService.findAll();
    }

    @PostMapping
    public BookDTO create(@RequestBody BookDTO book) {
        return bookService.create(book);
    }

    @PatchMapping
    public BookDTO update(@RequestBody BookDTO book) {
        return bookService.update(book);
    }

    @DeleteMapping(DELETE_ENTITY)
    public void delete(@PathVariable Long id) { bookService.delete(id); }

    @PatchMapping(SELL_BOOK)
    public void sell(@RequestBody BookDTO book, @PathVariable Long amount) { bookService.sell(book, amount); }

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type) throws IOException {
        return reportServiceFactory.getReportService(type).export();
    }
}
