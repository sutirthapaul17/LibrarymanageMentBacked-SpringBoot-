package com.example.LibraryManagement.Service.Impl;

import com.example.LibraryManagement.Dto.BookDto.BookRequestDto;
import com.example.LibraryManagement.Dto.BookDto.BookResponseDto;
import com.example.LibraryManagement.Entity.Author;
import com.example.LibraryManagement.Entity.Book;
import com.example.LibraryManagement.Repository.AuthorRepo;
import com.example.LibraryManagement.Repository.BookRepo;
import com.example.LibraryManagement.Service.BookService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
public class BookServiceImplementation implements BookService {

    private BookResponseDto mapToResponse(Book book) {

        BookResponseDto dto = new BookResponseDto();
        dto.setBid(book.getBid());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setCategory(book.getCategory());
        dto.setPrice(book.getPrice());
        dto.setReachedToLibrary(book.getReachedToLibrary());
        dto.setTotalCopies(book.getTotalCopies());
        dto.setAvailableCopies(book.getAvailableCopies());

        List<String> authorNames = book.getAuthors()
                .stream()
                .map(Author::getName)
                .toList();

        dto.setAuthorNames(authorNames);

        return dto;
    }


    private final BookRepo bookRepo;
    private final AuthorRepo authorRepo;


    @Override
    @Transactional
    public BookResponseDto addBook(BookRequestDto dto) {

        // 1️⃣ Fetch authors using IDs to map with book
        List<Author> authors = authorRepo.findAllById(dto.getAuthorIds());

        if (authors.size() != dto.getAuthorIds().size()) {
            throw new RuntimeException("One or more authors not found");
        }

        // 2️⃣ Map Request DTO → Entity
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setCategory(dto.getCategory());
        book.setPrice(dto.getPrice());
        book.setReachedToLibrary(dto.getReachedToLibrary());
        book.setTotalCopies(dto.getTotalCopies());
        book.setAvailableCopies(dto.getTotalCopies());
        book.setAuthors(authors);

        // 3️⃣ Save entity
        Book savedBook = bookRepo.save(book);

        // 4️⃣ Map Entity → Response DTO
        return mapToResponse(savedBook);
    }


    @Override
    @Transactional
    public BookResponseDto updateBook(Long bookId, BookRequestDto dto) {

        Book existingBook = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // 2️⃣ Fetch authors
        List<Author> authors = authorRepo.findAllById(dto.getAuthorIds());
        int newAvailableCopies = getNewAvailableCopies(dto, authors, existingBook);

        // 4️⃣ Update allowed fields
        existingBook.setTitle(dto.getTitle());
        existingBook.setIsbn(dto.getIsbn()); // optional: allow or block
        existingBook.setCategory(dto.getCategory());
        existingBook.setPrice(dto.getPrice());
        existingBook.setReachedToLibrary(dto.getReachedToLibrary());
        existingBook.setTotalCopies(dto.getTotalCopies());
        existingBook.setAvailableCopies(newAvailableCopies);
        existingBook.setAuthors(authors);

        // 5️⃣ Dirty checking will persist changes
        return mapToResponse(existingBook);
    }

    private static int getNewAvailableCopies(BookRequestDto dto, List<Author> authors, Book existingBook) {
        if (authors.size() != dto.getAuthorIds().size()) {
            throw new RuntimeException("One or more authors not found");
        }

        // 3️⃣ Handle totalCopies update safely
        int borrowedCopies =
                existingBook.getTotalCopies() - existingBook.getAvailableCopies();

        if (dto.getTotalCopies() < borrowedCopies) {
            throw new RuntimeException(
                    "Total copies cannot be less than borrowed copies"
            );
        }

        return dto.getTotalCopies() - borrowedCopies;
    }



    @Override
    @Transactional(readOnly = true)
    public BookResponseDto getBookById(Long bookId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        return mapToResponse(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> getAllBooks() {
        return bookRepo.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> getBooksByCategory(String category) {
        return bookRepo.findByCategory(category)
                .stream()
                .map(this :: mapToResponse)
                .toList();

    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> getBooksByAuthor(String authorName) {
        return bookRepo.findByAuthors_Name(authorName)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> getAvailableBooks() {
        return bookRepo.findByAvailableCopiesGreaterThan(0)
                .stream()
                .map(this :: mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteBook(Long bookId) {

        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // optional but IMPORTANT safety check
        if (book.getAvailableCopies() < book.getTotalCopies()) {
            throw new RuntimeException("Cannot delete a book that is currently borrowed");
        }

        bookRepo.deleteById(bookId);

    }
}
