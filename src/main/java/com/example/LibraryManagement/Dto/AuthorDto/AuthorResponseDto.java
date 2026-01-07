package com.example.LibraryManagement.Dto.AuthorDto;

import lombok.Data;

import java.util.List;


@Data
public class AuthorResponseDto {

    private Long aid;
    private String name;
    private String email;
    private String mobileNumber;

    private List<String> bookNames;
}
