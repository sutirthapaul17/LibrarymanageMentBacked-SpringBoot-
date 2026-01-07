package com.example.LibraryManagement.Repository;

import com.example.LibraryManagement.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member, Long> {
}