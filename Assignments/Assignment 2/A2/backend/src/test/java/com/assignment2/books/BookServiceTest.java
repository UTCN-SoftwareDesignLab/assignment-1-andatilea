package com.assignment2.books;
import com.assignment2.TestCreationFactory;
import com.assignment2.books.model.Book;
import com.assignment2.books.model.dto.BookDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository, bookMapper);
        bookService.deleteAll();
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(books.size(), all.size());
    }

    @Test
    public void create() {

        Book book = Book.builder()
                .id(TestCreationFactory.randomLong())
                .title(TestCreationFactory.randomString())
                .quantity(TestCreationFactory.randomLong())
                .author(TestCreationFactory.randomString())
                .price(TestCreationFactory.randomLong())
                .id(TestCreationFactory.randomLong())
                .build();

        BookDTO bookDTO = BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .quantity(book.getQuantity())
                .author(book.getAuthor())
                .price(book.getPrice())
                .id(book.getId())
                .build();

        when(bookMapper.fromDto(bookDTO)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        when(bookRepository.save(book)).thenReturn(book);
        assertEquals(bookDTO, bookService.create(bookDTO));
    }


    @Test
    public void update() {

        Book book = Book.builder()
                .id(TestCreationFactory.randomLong())
                .title(TestCreationFactory.randomString())
                .quantity(TestCreationFactory.randomLong())
                .author(TestCreationFactory.randomString())
                .price(TestCreationFactory.randomLong())
                .id(TestCreationFactory.randomLong())
                .build();

        BookDTO bookDTO = BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .quantity(5L)
                .author(book.getAuthor())
                .price(book.getPrice())
                .id(book.getId())
                .build();

        when(bookMapper.fromDto(bookDTO)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        Book book1 = bookService.findById(book.getId());
        book1.setQuantity(5L);
        assertEquals(book1.getQuantity(), bookService.update(bookMapper.toDto(book)).getQuantity());


    }

    @Test
    public void delete() {

        // firstly we add a book and save it
        BookDTO book = BookDTO.builder()
                .id(TestCreationFactory.randomLong())
                .title(TestCreationFactory.randomString())
                .author(TestCreationFactory.randomString())
                .genre(TestCreationFactory.randomString())
                .quantity(TestCreationFactory.randomLong())
                .price(TestCreationFactory.randomLong())
                .build();
        bookRepository.save(bookMapper.fromDto(book));

        // then we delete it
        bookRepository.delete(bookMapper.fromDto(book));

        // we return the list with all existing books
        List<BookDTO> all = bookService.findAll();

        // and its size should be 0 (since we deleted the one existing book)
        assertEquals(0, all.size());
    }

    @Test
    public void findByTitle()
    {
        Book book = Book.builder()
                .id(1L)
                .title("title")
                .author("author")
                .genre("genre")
                .quantity(1L)
                .price(1L)
                .build();


        BookDTO bookDTO = BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .quantity(book.getQuantity())
                .price(book.getPrice())
                .id(book.getId())
                .build();

        when(bookMapper.fromDto(bookDTO)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        when(bookService.create(bookDTO)).thenReturn(bookDTO);

        when(bookRepository.findByTitle("title")).thenReturn(book);
        assertEquals("title", bookService.findByTitle("title").getTitle());
    }

    @Test
    public void findByAuthor()
    {
        Book book = Book.builder()
                .id(1L)
                .title("title")
                .author("author")
                .genre("genre")
                .quantity(1L)
                .price(1L)
                .build();


        BookDTO bookDTO = BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .quantity(book.getQuantity())
                .price(book.getPrice())
                .id(book.getId())
                .build();

        when(bookMapper.fromDto(bookDTO)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        when(bookService.create(bookDTO)).thenReturn(bookDTO);

        when(bookRepository.findByAuthor("author")).thenReturn(book);
        assertEquals("author", bookService.findByAuthor("author").getAuthor());
    }

    @Test
    public void findByGenre()
    {
        Book book = Book.builder()
                .id(1L)
                .title("title")
                .author("author")
                .genre("genre")
                .quantity(1L)
                .price(1L)
                .build();


        BookDTO bookDTO = BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .quantity(book.getQuantity())
                .price(book.getPrice())
                .id(book.getId())
                .build();

        when(bookMapper.fromDto(bookDTO)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        when(bookService.create(bookDTO)).thenReturn(bookDTO);

        when(bookRepository.findByGenre("genre")).thenReturn(book);
        assertEquals("genre", bookService.findByGenre("genre").getGenre());
    }
}