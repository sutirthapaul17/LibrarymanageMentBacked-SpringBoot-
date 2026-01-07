package com.example.LibraryManagement.Dto.MemberDto;

import lombok.Data;

@Data
public class MemberResponseDto {

    private Long mid;
    private String name;
    private String membershipType;

    private Integer activeBorrowCount;
}

