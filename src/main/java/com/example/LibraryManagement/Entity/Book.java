package com.example.LibraryManagement.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false,unique = true)
    private String isbn;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private LocalDate reachedToLibrary;
    @Column(nullable = false)
    private int totalCopies;
    @Column(nullable = false)
    private int availableCopies;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    @OneToMany(mappedBy = "book",fetch = FetchType.LAZY)
    private List<BorrowRecord> borrowRecordList;

}
