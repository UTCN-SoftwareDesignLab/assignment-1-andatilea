package com.assignment2.books;
import com.assignment2.books.model.Book;
import com.assignment2.books.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO create(BookDTO book) {
        Book newBook = bookMapper.fromDto(book);
        return bookMapper.toDto(bookRepository.save(
                newBook));
    }

    public BookDTO update(BookDTO book) {
        Book actBook = findById(book.getId());
        actBook.setTitle(book.getTitle());
        actBook.setAuthor(book.getAuthor());
        actBook.setGenre(book.getGenre());
        actBook.setQuantity(book.getQuantity());
        actBook.setPrice(book.getPrice());
        return bookMapper.toDto(bookRepository.save(actBook));
    }

    public void delete(Long id) {
        Book actBook = findById(id);
        bookRepository.delete(actBook);
        System.out.println("Book deletion successful!");
    }

    public void sell(BookDTO book, Long amount) {

        Book actBook = findById(book.getId());
        if(actBook.getQuantity() >= amount) {
            actBook.setQuantity(book.getQuantity() - amount);
            bookRepository.save(actBook);
            System.out.println("Book sold successfully!");
        }
        else {
            System.out.println("Book out of stock");
        }
    }

    public void deleteAll (){
        bookRepository.deleteAll();
    }

    public BookDTO findByTitle(String title) {
        return bookMapper.toDto(bookRepository.findByTitle(title));
    }

    public BookDTO findByAuthor(String author) {
        return bookMapper.toDto(bookRepository.findByAuthor(author));
    }

    public BookDTO findByGenre(String genre) {
        return bookMapper.toDto(bookRepository.findByGenre(genre));
    }
}


