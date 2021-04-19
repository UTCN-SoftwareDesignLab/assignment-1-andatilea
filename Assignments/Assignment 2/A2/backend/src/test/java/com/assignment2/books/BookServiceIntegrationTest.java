package com.assignment2.books;

import com.assignment2.TestCreationFactory;
import com.assignment2.books.model.Book;
import com.assignment2.books.model.dto.BookDTO;
import com.assignment2.user.dto.UserMinimalDTO;
import com.assignment2.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        bookRepository.saveAll(books);

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(books.size(), all.size());
    }

    @Test
    void createAll(){
        int nrBooks = 10;
        List<BookDTO> books = new ArrayList<>();
        for (int i = 0; i < nrBooks; i++) {
            Book book = Book.builder()
                    .title("Title " + i)
                    .author("Author " + i)
                    .genre("Genre " + i)
                    .quantity(40L)
                    .price(10L)
                    .build();
            books.add(bookMapper.toDto(book));
            bookService.create(bookMapper.toDto(book));
        }

        List<BookDTO> all = bookService.findAll();
        assertEquals(nrBooks, all.size());
    }

    @Test
    void updateAll() {
        int nrBooks = 10;
        List<BookDTO> modifiedBook = new ArrayList<>();
        for (int i = 0; i < nrBooks; i++) {
            Book book = Book.builder()
                    .title("Title " + i)
                    .author("Author " + i)
                    .genre("Genre " + i)
                    .quantity(31L)
                    .price(30L)
                    .build();
            BookDTO bookInserted = bookService.create(bookMapper.toDto(book));
            bookInserted.setQuantity(15L);
            modifiedBook.add(bookService.update(bookInserted));
        }
        for(int i = 0;i < nrBooks; i++){
            assertEquals(modifiedBook.get(i).getQuantity(), 15L);

        }
    }

    @Test
    void deleteAll() {
        bookRepository.deleteAll();

        List<BookDTO> all = bookService.findAll();

        assertEquals(0, all.size());
    }
}
