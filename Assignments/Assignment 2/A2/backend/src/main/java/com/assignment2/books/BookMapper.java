package com.assignment2.books;
import com.assignment2.books.model.Book;
import com.assignment2.books.model.dto.BookDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface BookMapper {

    BookDTO toDto(Book book);

    Book fromDto(BookDTO book);
}
