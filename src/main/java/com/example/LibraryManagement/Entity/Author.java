package com.example.LibraryManagement.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter

public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aid;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = false, unique = true)
    private String mobileNumber;


    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();;
}
