package com.example.LibraryManagement.Controller;

import com.example.LibraryManagement.Dto.BorrowRecordDto.BorrowRecordResponseDto;
import com.example.LibraryManagement.Entity.BorrowRecord;
import com.example.LibraryManagement.Service.BorrowService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrow")
@AllArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;

    // Borrow a book
    @PostMapping("/{memberId}/{bookId}")
    public String borrowBook(@PathVariable Long memberId,
                             @PathVariable Long bookId) {
        borrowService.borrowBook(memberId, bookId);
        return "Book borrowed successfully";
    }

    // Return a book
    @PostMapping("/return/{borrowRecordId}")
    public String returnBook(@PathVariable Long borrowRecordId) {
        borrowService.returnBook(borrowRecordId);
        return "Book returned successfully";
    }

    // Borrow history of a member
    @GetMapping("/member/{memberId}/history")
    public List<BorrowRecordResponseDto> getBorrowHistory(@PathVariable Long memberId) {
        return borrowService.getBorrowHistoryByMember(memberId);
    }

    // Active borrows of a member
    @GetMapping("/member/{memberId}/active")
    public List<BorrowRecordResponseDto> getActiveBorrows(@PathVariable Long memberId) {
        return borrowService.getActiveBorrowsByMember(memberId);
    }

    // Overdue borrows
    @GetMapping("/overdue")
    public List<BorrowRecordResponseDto> getOverdueBorrows() {
        return borrowService.getOverdueBorrows();
    }
}
