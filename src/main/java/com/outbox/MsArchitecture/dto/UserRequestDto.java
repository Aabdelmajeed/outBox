package com.outbox.MsArchitecture.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @NotNull
    private String firstName;
    private String lastName;
    private String address;
    @NotNull
    private LocalDate dob;
    @Email
    private String email;
}

