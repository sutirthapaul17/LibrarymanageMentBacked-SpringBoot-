package com.example.LibraryManagement.Service;

import com.example.LibraryManagement.Dto.BookDto.BookRequestDto;
import com.example.LibraryManagement.Dto.BookDto.BookResponseDto;
import com.example.LibraryManagement.Entity.Book;

import java.util.List;

public interface BookService {


    BookResponseDto addBook(BookRequestDto book);

    BookResponseDto updateBook(Long bookId, BookRequestDto dto);

    BookResponseDto getBookById(Long bookId);

    List<BookResponseDto> getAllBooks();

    List<BookResponseDto> getBooksByCategory(String category);

    List<BookResponseDto> getBooksByAuthor(String authorName);

    List<BookResponseDto> getAvailableBooks();

    void deleteBook(Long bookId);
}
