package com.example.LibraryManagement.Service;

import com.example.LibraryManagement.Dto.AuthorDto.AuthorRequestDto;
import com.example.LibraryManagement.Dto.AuthorDto.AuthorResponseDto;
import com.example.LibraryManagement.Entity.Author;

import java.util.List;

public interface AuthorService {

    AuthorResponseDto addAuthor(AuthorRequestDto authorRequestDto);

    AuthorResponseDto updateAuthor(Long authorId, AuthorRequestDto author);

    AuthorResponseDto getAuthorById(Long authorId);

    List<AuthorResponseDto> getAllAuthors();

    void deleteAuthor(Long authorId);
}
