package com.example.LibraryManagement.Service;


import com.example.LibraryManagement.Dto.MemberDto.MemberRequestDto;
import com.example.LibraryManagement.Dto.MemberDto.MemberResponseDto;
import com.example.LibraryManagement.Entity.Member;

import java.util.List;

public interface MemberService {


    MemberResponseDto addMember(MemberRequestDto member);

    MemberResponseDto updateMember(Long memberId, MemberRequestDto member);

    MemberResponseDto getMemberById(Long memberId);

    List<MemberResponseDto> getAllMembers();

    void deleteMember(Long memberId);
}
