package com.example.LibraryManagement.Repository;

import com.example.LibraryManagement.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepo extends JpaRepository<Book,Long> {
    List<Book> findByCategory(String category);
    List<Book> findByPriceBetween(int min, int max);
    List<Book> findByAuthors_Name(String name);
    List<Book> findByAvailableCopiesGreaterThan(int count);
}
