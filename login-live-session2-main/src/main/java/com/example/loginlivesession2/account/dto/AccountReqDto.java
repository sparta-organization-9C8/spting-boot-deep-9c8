package com.example.loginlivesession2.account.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class AccountReqDto {

//    @NotBlank
//    private String username;
// 회원가입시 이메일 형식이 유효하지 않은 경우, 비밀번호가 영어대소문자 숫자, 특수문자를 모두 포함하지 않은 경우
//    @Email
//    @NotBlank(message = "{member.email.notblank}")
//    private String email;
//
//    @NotBlank(message = "{member.password.notblank}")
////    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]$"
////            , message = "{member.password.pattern}")
////    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[$@$!%*#?&.])[A-Za-z$@$!%*#?&.]$"
////            , message = "{member.password.pattern}")
//    private String password;


    @NotBlank(message = "이메일에 공백이 포함될 수 없습니다.")
    @Pattern(regexp = "^[a-zA-z0-9]+@[a-zA-Z0-9]+\\.[a-z]+$", message = "이메일 형식을 확인해 주세요.")
    private String email;


    @NotBlank(message = "닉네임에 공백이 포함될 수 없습니다.")
    private String username;


    @NotBlank(message = "비밀번호에 공백이 포함될 수 없습니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=-])[a-zA-Z0-9!@#$%^&*()_+=-]*$", message = "영문 소문자, 대문자, 숫자, 특수문자가 반드시 포함되어야 합니다.")
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
