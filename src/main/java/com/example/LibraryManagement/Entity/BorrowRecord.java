package com.example.LibraryManagement.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate borrowDate;
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

}
