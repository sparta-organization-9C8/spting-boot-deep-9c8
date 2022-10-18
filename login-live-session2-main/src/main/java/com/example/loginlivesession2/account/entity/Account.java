package com.example.loginlivesession2.account.entity;

import com.example.loginlivesession2.account.dto.AccountReqDto;
import com.example.loginlivesession2.like.entity.Like;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    @OneToMany(mappedBy = "account")
    List<Like> likes = new ArrayList<>();


    public Account(AccountReqDto accountReqDto) {
        this.username = accountReqDto.getUsername();
        this.email = accountReqDto.getEmail();
        this.password = accountReqDto.getPassword();
    }

}
