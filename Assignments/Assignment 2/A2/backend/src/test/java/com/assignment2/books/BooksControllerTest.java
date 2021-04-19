package com.assignment2.books;

import com.assignment2.BaseControllerTest;
import com.assignment2.TestCreationFactory;
import com.assignment2.UrlMapping;
import com.assignment2.books.model.Book;
import com.assignment2.books.model.dto.BookDTO;
import com.assignment2.report.CSVReportService;
import com.assignment2.report.PdfReportService;
import com.assignment2.report.ReportServiceFactory;
import com.assignment2.report.ReportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BooksControllerTest extends BaseControllerTest {

    @InjectMocks
    private BooksController controller;

    @Mock
    private BookService bookService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private CSVReportService csvReportService;

    @Mock
    private PdfReportService pdfReportService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BooksController(reportServiceFactory,bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        bookService.deleteAll();

    }

    @Test
    void allBooks() throws Exception {
        List<BookDTO> books = TestCreationFactory.listOf(Book.class);
        when(bookService.findAll()).thenReturn(books);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(UrlMapping.BOOK_STORE));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));

    }

    @Test
    void exportReport() throws Exception {
        when(reportServiceFactory.getReportService(ReportType.PDF)).thenReturn(pdfReportService);
        when(reportServiceFactory.getReportService(ReportType.CSV)).thenReturn(csvReportService);
        String pdfResponse = "PDF!";
        when(pdfReportService.export()).thenReturn(pdfResponse);
        String csvResponse = "CSV!";
        when(csvReportService.export()).thenReturn(csvResponse);

        ResultActions pdfExport = mockMvc.perform(MockMvcRequestBuilders.get(UrlMapping.BOOK_STORE + UrlMapping.EXPORT_REPORT, ReportType.PDF.name()));
        ResultActions csvExport = mockMvc.perform(MockMvcRequestBuilders.get(UrlMapping.BOOK_STORE + UrlMapping.EXPORT_REPORT, ReportType.CSV.name()));

        pdfExport.andExpect(status().isOk())
                .andExpect(content().string(pdfResponse));
        csvExport.andExpect(status().isOk())
                .andExpect(content().string(csvResponse));

    }

    @Test
    void create() throws Exception {

        BookDTO reqBook = BookDTO.builder()
                .title(TestCreationFactory.randomString())
                .author(TestCreationFactory.randomString())
                .genre(TestCreationFactory.randomString())
                .quantity(TestCreationFactory.randomLong())
                .price(TestCreationFactory.randomLong())
                .build();

        when(bookService.create(reqBook)).thenReturn(reqBook);

        ResultActions result = performPostWithRequestBody(UrlMapping.BOOK_STORE, reqBook);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void update() throws Exception {
        BookDTO reqBook = BookDTO.builder()
                .id(TestCreationFactory.randomLong())
                .title(TestCreationFactory.randomString())
                .author(TestCreationFactory.randomString())
                .quantity(TestCreationFactory.randomLong())
                .price(TestCreationFactory.randomLong())
                .build();

        when(bookService.update(reqBook)).thenReturn(reqBook);

        ResultActions result = performPatchWithRequestBody(UrlMapping.BOOK_STORE, reqBook);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void delete() throws Exception {

        BookDTO book = BookDTO.builder()
                .id(TestCreationFactory.randomLong())
                .title(TestCreationFactory.randomString())
                .author(TestCreationFactory.randomString())
                .genre(TestCreationFactory.randomString())
                .price(TestCreationFactory.randomLong())
                .quantity(TestCreationFactory.randomLong())
                .build();
        ResultActions result = performDeleteWithPathVariable(UrlMapping.BOOK_STORE+UrlMapping.DELETE_ENTITY,book.getId().toString());
        result.andExpect(status().isOk());
    }

    @Test
    void sell() throws Exception {
        BookDTO book = BookDTO.builder()
                .id(TestCreationFactory.randomLong())
                .title(TestCreationFactory.randomString())
                .author(TestCreationFactory.randomString())
                .genre(TestCreationFactory.randomString())
                .price(TestCreationFactory.randomLong())
                .quantity(TestCreationFactory.randomLong())
                .build();
        when(bookService.create(book)).thenReturn(book);
        bookService.sell(book, book.getQuantity());
        ResultActions result = performPatchWithRequestBodyAndVariable(UrlMapping.BOOK_STORE+UrlMapping.SELL_BOOK + book.getQuantity(), book, book.getQuantity().toString());
        result.andExpect(status().isOk());


    }

}