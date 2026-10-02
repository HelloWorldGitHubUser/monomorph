package com.hoangtien2k3.ecommerce.controller;

import com.hoangtien2k3.ecommerce.dto.request.Login;
import com.hoangtien2k3.ecommerce.dto.request.SignUp;
import com.hoangtien2k3.ecommerce.dto.response.TokenValidationResponse;
import com.hoangtien2k3.ecommerce.dto.response.InformationMessage;
import com.hoangtien2k3.ecommerce.dto.response.JwtResponseMessage;
import com.hoangtien2k3.ecommerce.dto.response.ResponseMessage;
import com.hoangtien2k3.ecommerce.security.jwt.JwtProvider;
import com.hoangtien2k3.ecommerce.security.validate.AuthorityTokenUtil;
import com.hoangtien2k3.ecommerce.service.UserService;
import com.hoangtien2k3.ecommerce.security.validate.TokenValidate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "User Authentication API", description = "APIs for user registration, login, and authentication")
@RequiredArgsConstructor
public class UserAuth {

    private final UserService userService;
    private final TokenValidate tokenValidate;
    private final AuthorityTokenUtil authorityTokenUtil;

    @Operation(summary = "Register a new user", description = "Registers a new user with the provided details.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping({ "/signup", "/register" })
    public ResponseEntity<ResponseMessage> register(@Valid @RequestBody SignUp signUp) {
        try {
            userService.register(signUp);
            return ResponseEntity.ok(new ResponseMessage("Create user: " + signUp.getUsername() + " successfully."));
        } catch (Exception e) {
            log.error("Register failed: {}", e.getMessage(), e);
            return ResponseEntity.ok(new ResponseMessage("Error: " + e.getMessage()));
        }
    }

    @Operation(summary = "User login", description = "Logs in a user with the provided credentials.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping({ "/signin", "/login" })
    public ResponseEntity<JwtResponseMessage> login(@Valid @RequestBody Login signInForm) {
        try {
            JwtResponseMessage jwt = userService.login(signInForm);
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            log.error("Login failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new JwtResponseMessage(null, null, new InformationMessage()));
        }
    }

    @Operation(summary = "User logout", description = "Logs out the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Logged out successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated() and hasAuthority('USER')")
    public ResponseEntity<String> logout() {
        log.info("Logout endpoint called");
        try {
            userService.logout();
            return ResponseEntity.ok("Logged out successfully.");
        } catch (Exception error) {
            log.error("Logout failed", error);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Logout failed.");
        }
    }

    @Operation(summary = "Validate JWT token", description = "Validates the provided JWT token.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token is valid"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping({ "/validateToken", "/validate-token" })
    public ResponseEntity<?> validateToken(@RequestHeader(name = "Authorization") String authorizationToken) {
        if (tokenValidate.validateToken(authorizationToken)) {
            return ResponseEntity.ok(new TokenValidationResponse("Valid token"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new TokenValidationResponse("Invalid token"));
        }
    }

    @Operation(summary = "Check user authority", description = "Checks if the user has the specified authority.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role access API"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping({ "/hasAuthority", "/authorization" })
    public ResponseEntity<?> getAuthority(@RequestHeader(name = "Authorization") String authorizationToken,
            @RequestParam String requiredRole) {
        List<String> authorities = authorityTokenUtil.checkPermission(authorizationToken);

        if (authorities.contains(requiredRole)) {
            return ResponseEntity.ok(new TokenValidationResponse("Role access api"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenValidationResponse("Invalid token"));
        }
    }

}
