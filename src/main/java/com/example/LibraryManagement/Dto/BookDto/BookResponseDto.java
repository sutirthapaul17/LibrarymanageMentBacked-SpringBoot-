package com.example.LibraryManagement.Dto.BookDto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
public class BookResponseDto {

    private Long bid;

    private String title;
    private String isbn;
    private String category;

    private Integer price;
    private LocalDate reachedToLibrary;

    private Integer totalCopies;
    private Integer availableCopies;

    private List<String> authorNames;
}

