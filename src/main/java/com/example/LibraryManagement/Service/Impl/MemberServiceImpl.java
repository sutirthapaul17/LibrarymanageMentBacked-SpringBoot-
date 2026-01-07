package com.example.LibraryManagement.Service.Impl;

import com.example.LibraryManagement.Dto.MemberDto.MemberRequestDto;
import com.example.LibraryManagement.Dto.MemberDto.MemberResponseDto;
import com.example.LibraryManagement.Entity.Member;
import com.example.LibraryManagement.Repository.MemberRepo;
import com.example.LibraryManagement.Service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@AllArgsConstructor
@Service
@org.springframework.transaction.annotation.Transactional
public class MemberServiceImpl implements MemberService {


    private final MemberRepo memberRepo;

    // 1️⃣ ADD MEMBER
    @Override
    public MemberResponseDto addMember(MemberRequestDto dto) {

        Member member = new Member();
        member.setName(dto.getName());
        member.setMembershipType(dto.getMembershipType());

        Member savedMember = memberRepo.save(member);
        return mapToResponse(savedMember);
    }

    // 2️⃣ UPDATE MEMBER
    @Override
    public MemberResponseDto updateMember(Long memberId, MemberRequestDto dto) {

        Member existingMember = memberRepo.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        existingMember.setName(dto.getName());
        existingMember.setMembershipType(dto.getMembershipType());

        // dirty checking
        return mapToResponse(existingMember);
    }

    // 3️⃣ GET MEMBER BY ID
    @Override
    public MemberResponseDto getMemberById(Long memberId) {

        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        return mapToResponse(member);
    }

    // 4️⃣ GET ALL MEMBERS
    @Override
    @Transactional(readOnly = true)
    public List<MemberResponseDto> getAllMembers() {

        return memberRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }



    // 5️⃣ DELETE MEMBER
    @Override
    public void deleteMember(Long memberId) {

        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // optional but realistic safety check
        boolean hasActiveBorrows = member.getRecords()
                .stream()
                .anyMatch(record -> record.getReturnDate() == null);

        if (hasActiveBorrows) {
            throw new RuntimeException("Cannot delete member with active borrowed books");
        }

        memberRepo.deleteById(memberId);
    }


    // 🔁 MAPPER METHOD (PRIVATE & CLEAN)
    private MemberResponseDto mapToResponse(Member member) {

        MemberResponseDto dto = new MemberResponseDto();
        dto.setMid(member.getMid());
        dto.setName(member.getName());
        dto.setMembershipType(member.getMembershipType());

        return dto;
    }
}
