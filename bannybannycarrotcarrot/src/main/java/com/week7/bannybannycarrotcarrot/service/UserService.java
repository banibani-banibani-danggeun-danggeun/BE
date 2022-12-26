package com.week7.bannybannycarrotcarrot.service;

import com.week7.bannybannycarrotcarrot.dto.MsgDto;
import com.week7.bannybannycarrotcarrot.dto.UserDto;
import com.week7.bannybannycarrotcarrot.entity.User;
import com.week7.bannybannycarrotcarrot.repository.UserRepository;
import com.week7.bannybannycarrotcarrot.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    public MsgDto.ResponseDto signup(UserDto.SignupRequestDto requestDto) {
        String password = "";

        if(userRepository.existsByUsername(requestDto.username()))
            return new MsgDto.ResponseDto("이미 존재하는 아이디입니다.", HttpStatus.BAD_REQUEST.value());

        if(userRepository.existsByNickname(requestDto.nickname()))
            return new MsgDto.ResponseDto("이미 존재하는 닉네임입니다.", HttpStatus.BAD_REQUEST.value());


        if (!Objects.equals(requestDto.passwordCheck(), requestDto.password()))
            return new MsgDto.ResponseDto("비밀번호 확인란과 비밀번호란이 일치하지 않습니다.", HttpStatus.BAD_REQUEST.value());
        password = passwordEncoder.encode(requestDto.password());
        userRepository.save(new User(requestDto.username(), password, requestDto.nickname()));
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
