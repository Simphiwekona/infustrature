package com.example.infustratureportal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {

    private String email;
    private String password;
    private String username;

    public RegisterUserDto(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public RegisterUserDto() {
    }
}
