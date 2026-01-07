package com.example.LibraryManagement.Dto.BorrowRecordDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BorrowRecordRequestDto {

    @NotNull(message = "Member ID is required")
    private Long memberId;

    @NotNull(message = "Book ID is required")
    private Long bookId;
}
