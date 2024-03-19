package com.cgi.api.controllers;

import com.cgi.api.dto.*;
import com.cgi.api.dto.auth.AuthenticationRequest;
import com.cgi.api.dto.auth.AuthenticationResponse;
import com.cgi.api.dto.auth.TokenRefreshRequest;
import com.cgi.api.dto.auth.TokenRefreshResponse;
import com.cgi.api.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "API operations with Authentication")
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Validated
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Register new account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content(mediaType = "*/*"))
    })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid UserDto userDto) {
        log.info("REST request to register User");
        AuthenticationResponse res = authenticationService.register(userDto);
        return ResponseEntity.ok().body(res);
    }

    @Operation(summary = "Login to account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "403", description = "Invalid data", content = @Content(mediaType = "*/*"))
    })
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        log.info("REST request to login {}", request.getEmail());
        return ResponseEntity.ok().body(authenticationService.authenticate(request));
    }

    @Operation(summary = "Refresh Authorization Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenRefreshResponse.class))),
            @ApiResponse(responseCode = "401", description = "Refresh token was expired", content = @Content(mediaType = "*/*"))
    })
    @PostMapping("/refresh_token")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestBody @Valid TokenRefreshRequest request) {
        log.info("REST request to refresh token {}", request.getRefreshToken());
        TokenRefreshResponse response = authenticationService.refreshToken(request);
        return ResponseEntity.ok().body(response);
    }
}
