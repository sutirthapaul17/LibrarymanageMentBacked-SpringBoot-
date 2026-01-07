package com.example.LibraryManagement.Repository;

import com.example.LibraryManagement.Entity.BorrowRecord;
import com.example.LibraryManagement.Entity.Member;
import org.hibernate.sql.results.graph.FetchList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BorrowRecordRepo extends JpaRepository<BorrowRecord, Long> {

    List<BorrowRecord> findByMember(Member member);

    List<BorrowRecord> findByMemberAndReturnDateIsNull(Member member);

    List<BorrowRecord> findByReturnDateIsNullAndBorrowDateBefore(LocalDate date);

    List<BorrowRecord> findByMember_MidAndReturnDateIsNull(Long memberId);

    List<BorrowRecord> findByMember_Mid(Long memberId);

}
