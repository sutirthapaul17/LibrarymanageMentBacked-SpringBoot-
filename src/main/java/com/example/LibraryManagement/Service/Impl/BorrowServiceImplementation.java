package com.example.LibraryManagement.Service.Impl;

import com.example.LibraryManagement.Dto.BorrowRecordDto.BorrowRecordResponseDto;
import com.example.LibraryManagement.Entity.Book;
import com.example.LibraryManagement.Entity.BorrowRecord;
import com.example.LibraryManagement.Entity.Member;
import com.example.LibraryManagement.Repository.BookRepo;
import com.example.LibraryManagement.Repository.BorrowRecordRepo;
import com.example.LibraryManagement.Repository.MemberRepo;
import com.example.LibraryManagement.Service.BorrowService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
@Service
@AllArgsConstructor
@Transactional
public class BorrowServiceImplementation implements BorrowService {

    private final BookRepo bookRepo;
    private final BorrowRecordRepo borrowRecordRepo;
    private final MemberRepo memberRepo;

    // 1️⃣ BORROW BOOK
    @Override
    public BorrowRecordResponseDto borrowBook(Long memberId, Long bookId) {

        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        if (!canMemberBorrow(member)) {
            throw new RuntimeException("Borrow limit exceeded");
        }

        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (!isBookAvailable(book)) {
            throw new RuntimeException("Book not available");
        }

        BorrowRecord record = new BorrowRecord();
        record.setMember(member);
        record.setBook(book);
        record.setBorrowDate(LocalDate.now());
        record.setReturnDate(null);

        book.setAvailableCopies(book.getAvailableCopies() - 1);

        BorrowRecord saved = borrowRecordRepo.save(record);
        return mapToResponse(saved);
    }

    // 2️⃣ RETURN BOOK
    @Override
    public BorrowRecordResponseDto returnBook(Long borrowRecordId) {

        BorrowRecord record = borrowRecordRepo.findById(borrowRecordId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        if (record.getReturnDate() != null) {
            throw new RuntimeException("Book already returned");
        }

        record.setReturnDate(LocalDate.now());

        Book book = record.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);

        return mapToResponse(record);
    }

    // 3️⃣ BORROW HISTORY
    @Override
    @Transactional(readOnly = true)
    public List<BorrowRecordResponseDto> getBorrowHistoryByMember(Long memberId) {

        return borrowRecordRepo.findByMember_Mid(memberId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 4️⃣ ACTIVE BORROWS
    @Override
    @Transactional(readOnly = true)
    public List<BorrowRecordResponseDto> getActiveBorrowsByMember(Long memberId) {

        return borrowRecordRepo
                .findByMember_MidAndReturnDateIsNull(memberId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 5️⃣ OVERDUE BORROWS
    @Override
    @Transactional(readOnly = true)
    public List<BorrowRecordResponseDto> getOverdueBorrows() {

        LocalDate overdueDate = LocalDate.now().minusDays(14);

        return borrowRecordRepo
                .findByReturnDateIsNullAndBorrowDateBefore(overdueDate)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =====================
    // 🔒 PRIVATE HELPERS
    // =====================

    private boolean isBookAvailable(Book book) {
        return book.getAvailableCopies() > 0;
    }

    private boolean canMemberBorrow(Member member) {
        long activeBorrows = member.getRecords()
                .stream()
                .filter(r -> r.getReturnDate() == null)
                .count();

        return activeBorrows < 5;
    }

    private double calculateFine(BorrowRecord record) {

        if (record.getReturnDate() == null) {
            return 0.0;
        }

        long daysBorrowed =
                java.time.temporal.ChronoUnit.DAYS.between(
                        record.getBorrowDate(),
                        record.getReturnDate()
                );

        long overdueDays = Math.max(0, daysBorrowed - 14);
        return overdueDays * 5.0;
    }

    private BorrowRecordResponseDto mapToResponse(BorrowRecord record) {

        BorrowRecordResponseDto dto = new BorrowRecordResponseDto();
        dto.setBorrowId(record.getId());
        dto.setBorrowDate(record.getBorrowDate());
        dto.setReturnDate(record.getReturnDate());
        dto.setBookTitle(record.getBook().getTitle());
        dto.setMemberName(record.getMember().getName());
        dto.setFine(calculateFine(record));

        return dto;
    }
}