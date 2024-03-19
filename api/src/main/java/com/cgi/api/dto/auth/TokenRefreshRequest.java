package com.cgi.api.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TokenRefreshRequest {
    @NotEmpty(message = "Field cannot be empty")
    private String refreshToken;
}
