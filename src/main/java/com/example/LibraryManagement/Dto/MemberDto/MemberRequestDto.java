package com.example.LibraryManagement.Dto.MemberDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class MemberRequestDto {

    @NotBlank(message = "Member name cannot be empty")
    private String name;

    @NotBlank(message = "Membership type is required")
    @Pattern(
            regexp = "STANDARD|PREMIUM",
            message = "Membership type must be STANDARD or PREMIUM"
    )
    private String membershipType;
}

