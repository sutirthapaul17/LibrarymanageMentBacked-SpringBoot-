package com.example.LibraryManagement.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mid;

    private String name;

    private String membershipType;

    @OneToMany(mappedBy = "member")
    private List<BorrowRecord> records;


}
