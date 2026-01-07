package com.example.LibraryManagement.Controller;

import com.example.LibraryManagement.Dto.MemberDto.MemberRequestDto;
import com.example.LibraryManagement.Dto.MemberDto.MemberResponseDto;
import com.example.LibraryManagement.Entity.Member;
import com.example.LibraryManagement.Service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public MemberResponseDto addMember(@RequestBody MemberRequestDto member) {
        return memberService.addMember(member);
    }

    @PutMapping("/{id}")
    public MemberResponseDto updateMember(@PathVariable Long id,
                               @RequestBody MemberRequestDto member) {
        return memberService.updateMember(id, member);
    }

    @GetMapping("/{id}")
    public MemberResponseDto getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id);
    }

    @GetMapping
    public List<MemberResponseDto> getAllMembers() {
        return memberService.getAllMembers();
    }

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }
}
