package com.week7.bannybannycarrotcarrot.service;

import com.week7.bannybannycarrotcarrot.dto.MsgDto;
import com.week7.bannybannycarrotcarrot.dto.UserDto;
import com.week7.bannybannycarrotcarrot.repository.UserRepository;
import com.week7.bannybannycarrotcarrot.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    public MsgDto.ResponseDto signup(UserDto.SignupRequestDto requestDto) {
        if (requestDto.passwordCheck() != requestDto.password())
            return new MsgDto.ResponseDto("비밀번호 확인란과 비밀번호란이 일치하지 않습니다.", HttpStatus.BAD_REQUEST.value());
        userRepository.save(requestDto.toEntity());
        return new MsgDto.ResponseDto("회원가입 되었습니다.", HttpStatus.OK.value());
    }

    public MsgDto.ResponseDto login(UserDto.LoginRequestDto dto, HttpServletResponse httpServletResponse){
        //아직 인증 전 객체
        UsernamePasswordAuthenticationToken beforeAuthentication = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());

        //인증 완료된 인증 객체
        Authentication afterAuthentication = authenticationManagerBuilder.getObject().authenticate(beforeAuthentication);

        //인증 완료된 객체로 JWT 생성
        httpServletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.generateToken(afterAuthentication));

        return new MsgDto.ResponseDto("로그인 되었습니다.", HttpStatus.OK.value());
    }

    public MsgDto.ResponseDto idCheck(String username) {

        if (userRepository.existsByUsername(username)) {
            return new MsgDto.ResponseDto("이미 사용중인 아이디입니다.", HttpStatus.BAD_REQUEST.value());
        }

        return new MsgDto.ResponseDto("사용 가능한 아이디입니다.", HttpStatus.OK.value());
    }
}
