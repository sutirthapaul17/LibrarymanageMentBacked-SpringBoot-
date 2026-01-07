
package com.example.LibraryManagement.Controller;

import com.example.LibraryManagement.Dto.AuthorDto.AuthorRequestDto;
import com.example.LibraryManagement.Dto.AuthorDto.AuthorResponseDto;
import com.example.LibraryManagement.Entity.Author;
import com.example.LibraryManagement.Service.AuthorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@AllArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public AuthorResponseDto addAuthor(
            @Valid @RequestBody AuthorRequestDto authorRequestDto) {
        return authorService.addAuthor(authorRequestDto);
    }

    @GetMapping("/{id}")
    public AuthorResponseDto getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping
    public List<AuthorResponseDto> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @PutMapping("/{id}")
    public AuthorResponseDto updateAuthor(@PathVariable Long id,
                               @Valid @RequestBody AuthorRequestDto author) {
        return authorService.updateAuthor(id, author);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {

        authorService.deleteAuthor(id);

        return ResponseEntity.ok("Author deleted successfully");
    }

}
