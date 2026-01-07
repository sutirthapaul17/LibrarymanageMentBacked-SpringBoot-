package com.example.LibraryManagement.Service;

import com.example.LibraryManagement.Dto.BorrowRecordDto.BorrowRecordResponseDto;
import com.example.LibraryManagement.Entity.BorrowRecord;

import java.util.List;

public interface BorrowService {

    BorrowRecordResponseDto borrowBook(Long memberId, Long bookId);

    BorrowRecordResponseDto returnBook(Long borrowRecordId);

    List<BorrowRecordResponseDto> getBorrowHistoryByMember(Long memberId);

    List<BorrowRecordResponseDto> getActiveBorrowsByMember(Long memberId);


    List<BorrowRecordResponseDto> getOverdueBorrows();


}
