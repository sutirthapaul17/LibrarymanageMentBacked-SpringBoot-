package com.example.LibraryManagement.Controller;

import com.example.LibraryManagement.Dto.BookDto.BookRequestDto;
import com.example.LibraryManagement.Dto.BookDto.BookResponseDto;
import com.example.LibraryManagement.Entity.Book;
import com.example.LibraryManagement.Service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public BookResponseDto addBook(@RequestBody BookRequestDto book) {
        return bookService.addBook(book);
    }

    @PutMapping("/{id}")
    public BookResponseDto updateBook(@PathVariable Long id,
                           @RequestBody BookRequestDto book) {
        return bookService.updateBook(id, book);
    }

    @GetMapping("/{id}")
    public BookResponseDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping
    public List<BookResponseDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/category/{category}")
    public List<BookResponseDto> getBooksByCategory(@PathVariable String category) {
        return bookService.getBooksByCategory(category);
    }

    @GetMapping("/author/{name}")
    public List<BookResponseDto> getBooksByAuthor(@PathVariable String name) {
        return bookService.getBooksByAuthor(name);
    }

    @GetMapping("/available")
    public List<BookResponseDto> getAvailableBooks() {
        return bookService.getAvailableBooks();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);

        return ResponseEntity.ok("Book deleted Successfully");
    }
}
