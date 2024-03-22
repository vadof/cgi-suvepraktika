package com.cgi.api.dto.auth;

import com.cgi.api.dto.common.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String refreshToken;
    private UserDto user;
}
