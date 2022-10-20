package com.example.loginlivesession2.account.service;

import com.example.loginlivesession2.account.repository.AccountRepository;
import com.example.loginlivesession2.account.repository.RefreshTokenRepository;
import com.example.loginlivesession2.account.dto.AccountReqDto;
import com.example.loginlivesession2.account.dto.AccountResponseDto;
import com.example.loginlivesession2.account.dto.LoginReqDto;
import com.example.loginlivesession2.account.entity.Account;
import com.example.loginlivesession2.account.entity.RefreshToken;
import com.example.loginlivesession2.comment.dto.CommentResponseDto;
import com.example.loginlivesession2.comment.entity.Comment;
import com.example.loginlivesession2.exception.CustomException;
import com.example.loginlivesession2.exception.ErrorCode;
import com.example.loginlivesession2.global.dto.GlobalResDto;
import com.example.loginlivesession2.jwt.dto.TokenDto;
import com.example.loginlivesession2.jwt.util.JwtUtil;
import com.example.loginlivesession2.like.dto.LikeResponseDto;
import com.example.loginlivesession2.like.entity.Like;
import com.example.loginlivesession2.post.dto.PostMyPageResDto;
import com.example.loginlivesession2.post.dto.PostResponseDto;
import com.example.loginlivesession2.post.entity.Post;
import com.example.loginlivesession2.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public GlobalResDto signup(AccountReqDto accountReqDto) {
        // email 중복 검사
        if(accountRepository.findByEmail(accountReqDto.getEmail()).isPresent()){
            throw new CustomException(ErrorCode.AlreadyHaveEmail);
        }

        accountReqDto.setEncodePwd(passwordEncoder.encode(accountReqDto.getPassword()));
        Account account = new Account(accountReqDto);

        accountRepository.save(account);
        return new GlobalResDto("Success signup", HttpStatus.OK.value());
    }

    @Transactional
    public GlobalResDto login(LoginReqDto loginReqDto, HttpServletResponse response) {

        Account account = accountRepository.findByEmail(loginReqDto.getEmail()).orElseThrow(
                () -> new RuntimeException("Not found Account")
        );

        if(!passwordEncoder.matches(loginReqDto.getPassword(), account.getPassword())) {
            throw new RuntimeException("Not matches Password");
        }

        TokenDto tokenDto = jwtUtil.createAllToken(loginReqDto.getEmail());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByAccountEmail(loginReqDto.getEmail());

        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        }else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), loginReqDto.getEmail());
            refreshTokenRepository.save(newToken);
        }

        setHeader(response, tokenDto);

        return new GlobalResDto("Success Login", HttpStatus.OK.value());

    }
    // 내정보 조회
    @Transactional
    public AccountResponseDto getAccount(UserDetailsImpl userDetails) {
        Account account = accountRepository.findById(userDetails.getAccount().getUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.NotFoundUser)
        );
        List<Post> myPosts = account.getPost();
        List<PostMyPageResDto> myPostsList = new ArrayList<>();
        for (Post post : myPosts) {
            myPostsList.add(new PostMyPageResDto(post));
        }

        List<Comment> myComments = account.getComment();
        List<CommentResponseDto> myCommentList = new ArrayList<>();
        for (Comment comment : myComments) {
            myCommentList.add(new CommentResponseDto(comment));
        }

        List<Like> myLikes = account.getLikes();
        List<LikeResponseDto> myLikeList = new ArrayList<>();
        for (Like like : myLikes) {
            myLikeList.add(new LikeResponseDto(like));
        }

        List<Long> myLikePostId = new ArrayList<>();

        return new AccountResponseDto(account, myPostsList, myCommentList, myLikeList);
    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }
}
