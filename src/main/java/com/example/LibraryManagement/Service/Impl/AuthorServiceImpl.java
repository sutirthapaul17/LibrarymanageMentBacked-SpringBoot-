package com.example.LibraryManagement.Service.Impl;


import com.example.LibraryManagement.Dto.AuthorDto.AuthorRequestDto;
import com.example.LibraryManagement.Dto.AuthorDto.AuthorResponseDto;
import com.example.LibraryManagement.Entity.Author;
import com.example.LibraryManagement.Entity.Book;
import com.example.LibraryManagement.Repository.AuthorRepo;
import com.example.LibraryManagement.Service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepo authorRepo;

    @Override
    @Transactional
    public AuthorResponseDto addAuthor(AuthorRequestDto dto) {

        // 1️⃣ Map Request DTO → Entity
        Author author = new Author();
        author.setName(dto.getName());
        author.setEmail(dto.getEmail());
        author.setMobileNumber(dto.getMobileNumber());

        // 2️⃣ Save entity
        Author savedAuthor = authorRepo.save(author);

        // 3️⃣ Map Entity → Response DTO
        AuthorResponseDto response = new AuthorResponseDto();
        response = mapToResponse(savedAuthor);

        return response;
    }


    @Override
    @Transactional
    public AuthorResponseDto updateAuthor(Long authorId, AuthorRequestDto dto) {
        Author existingAuthor = authorRepo.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        // 1️⃣ Update allowed fields
        existingAuthor.setName(dto.getName());
        existingAuthor.setEmail(dto.getEmail());
        existingAuthor.setMobileNumber(dto.getMobileNumber());

        // 2️⃣ Map Entity → Response DTO
        AuthorResponseDto response = new AuthorResponseDto();
        response = mapToResponse(existingAuthor);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorResponseDto getAuthorById(Long authorId) {
        Author author = authorRepo.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return mapToResponse(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorResponseDto> getAllAuthors() {
        return authorRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteAuthor(Long authorId) {

        if (!authorRepo.existsById(authorId)) {
            throw new RuntimeException("Author not found");
        }

        authorRepo.deleteById(authorId);
    }










    private AuthorResponseDto mapToResponse(Author author) {
        AuthorResponseDto dto = new AuthorResponseDto();
        dto.setAid(author.getAid());
        dto.setName(author.getName());
        dto.setEmail(author.getEmail());
        dto.setMobileNumber(author.getMobileNumber());

        List<String> bookNames = author.getBooks()
                .stream()
                .map(Book::getTitle)
                .toList();

        dto.setBookNames(bookNames);
        return dto;
    }

}
