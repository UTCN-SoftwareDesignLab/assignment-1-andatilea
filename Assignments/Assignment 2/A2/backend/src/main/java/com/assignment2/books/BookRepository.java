package com.assignment2.books;
import com.assignment2.books.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByTitle(String title);

    Book findByAuthor(String author);

    Book findByGenre(String genre);
}
