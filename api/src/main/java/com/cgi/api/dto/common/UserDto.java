package com.cgi.api.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    @NotEmpty(message = "Field cannot be empty")
    private String firstname;

    @NotEmpty(message = "Field cannot be empty")
    private String lastname;

    @NotEmpty(message = "Field cannot be empty")
    @Email(message = "Invalid email")
    private String email;

    @Size(min = 8, message = "Min password length is 8")
    @NotEmpty(message = "Field cannot be empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
