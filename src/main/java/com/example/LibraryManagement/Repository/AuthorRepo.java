package com.example.LibraryManagement.Repository;

import com.example.LibraryManagement.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author, Long> {
}