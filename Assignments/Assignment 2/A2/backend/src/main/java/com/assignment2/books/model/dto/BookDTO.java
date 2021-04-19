package com.assignment2.books.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BookDTO {

    private Long id;
    private String title;
    private String author;
    private String genre;
    private Long quantity;
    private Long price;

}
