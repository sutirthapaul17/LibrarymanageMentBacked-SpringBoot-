package com.example.LibraryManagement.Dto.BorrowRecordDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BorrowRecordResponseDto {

    private Long borrowId;

    private String memberName;
    private String bookTitle;

    private LocalDate borrowDate;
    private LocalDate returnDate;
    private double fine;
}
