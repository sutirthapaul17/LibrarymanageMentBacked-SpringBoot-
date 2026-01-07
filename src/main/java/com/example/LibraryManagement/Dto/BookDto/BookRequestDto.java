package com.example.LibraryManagement.Dto.BookDto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookRequestDto {

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 2, max = 150, message = "Title must be between 2 and 150 characters")
    private String title;

    @NotBlank(message = "ISBN cannot be empty")
    @Size(min = 10, max = 20, message = "ISBN length must be between 10 and 20 characters")
    private String isbn;

    @NotBlank(message = "Category cannot be empty")
    private String category;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Integer price;

    @NotNull(message = "Reached-to-library date is required")
    @PastOrPresent(message = "Reached-to-library date cannot be in the future")
    private LocalDate reachedToLibrary;

    @NotNull(message = "Total copies is required")
    @Positive(message = "Total copies must be greater than 0")
    private Integer totalCopies;

    @NotEmpty(message = "At least one author must be provided")
    private List<@NotNull Long> authorIds;
}

