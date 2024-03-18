package com.cgi.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotEmpty(message = "Field cannot be empty")
    @Email(message = "Invalid email")
    private String email;
    @NotEmpty(message = "Field cannot be empty")
    private String password;

}
