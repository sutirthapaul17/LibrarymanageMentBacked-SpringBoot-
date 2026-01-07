package com.example.LibraryManagement.Dto.AuthorDto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;



@Data
public class AuthorRequestDto {

    @NotBlank(message = "Name can't be empty")
    private String name;

    @NotBlank(message = "Email can't be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Mobile number can't be empty")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Mobile number must be exactly 10 digits"
    )
    private String mobileNumber;
}
