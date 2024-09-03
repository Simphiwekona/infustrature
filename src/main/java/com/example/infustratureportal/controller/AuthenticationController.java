package com.example.infustratureportal.controller;

import com.example.infustratureportal.dto.LoginUserDto;
import com.example.infustratureportal.dto.RegisterUserDto;
import com.example.infustratureportal.dto.VerifyUserDto;
import com.example.infustratureportal.model.User;
import com.example.infustratureportal.response.LoginResponse;
import com.example.infustratureportal.service.AuthenticationService;
import com.example.infustratureportal.service.JWTService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JWTService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JWTService jwtService, AuthenticationService authenticationService)
    {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }
    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser (@RequestBody VerifyUserDto verifyUserDto) {
        try {
            authenticationService.verifyUser(verifyUserDto);
            return ResponseEntity.ok("Account verified successfully");
        }catch(RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {
        try {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification code sent");
        }catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
