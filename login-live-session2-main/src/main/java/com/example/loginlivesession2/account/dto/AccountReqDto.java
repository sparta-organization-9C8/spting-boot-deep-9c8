package com.example.loginlivesession2.account.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class AccountReqDto {

    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String password;


    public AccountReqDto(String email, String password, String username) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void setEncodePwd(String encodePwd) {
        this.password = encodePwd;
    }

}
