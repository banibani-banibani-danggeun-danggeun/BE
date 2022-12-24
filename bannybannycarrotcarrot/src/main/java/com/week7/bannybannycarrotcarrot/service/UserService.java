package com.week7.bannybannycarrotcarrot.service;

import com.week7.bannybannycarrotcarrot.dto.MsgDto;
import com.week7.bannybannycarrotcarrot.dto.UserDto;
import com.week7.bannybannycarrotcarrot.entity.User;
import com.week7.bannybannycarrotcarrot.errorcode.UserStatusCode;
import com.week7.bannybannycarrotcarrot.exception.RestApiException;
import com.week7.bannybannycarrotcarrot.repository.UserRepository;
import com.week7.bannybannycarrotcarrot.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    public void signup(UserDto.SignupRequestDto requestDto) {
        String password = "";

        if(userRepository.existsByUsername(requestDto.username()))
            throw new RestApiException(UserStatusCode.OVERLAPPED_USERNAME);


        if (!Objects.equals(requestDto.passwordCheck(), requestDto.password()))
            throw new RestApiException(UserStatusCode.OVERLAPPED_USERNAME);
//            return new MsgDto.ResponseDto("비밀번호 확인란과 비밀번호란이 일치하지 않습니다.", HttpStatus.BAD_REQUEST.value());
        password = passwordEncoder.encode(requestDto.password());
        userRepository.save(new User(requestDto.username(), password));

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
            throw  new RestApiException(UserStatusCode.OVERLAPPED_USERNAME);
        }
        //아이디
        if (Pattern.matches("^[a-z0-9]*$",username)){
            if(username.length() < 8 || username.length() > 15){
                throw new RestApiException(UserStatusCode)
            }
        } else {
            throw new RestApiException()
        }



        return new MsgDto.ResponseDto("사용 가능한 아이디입니다.", HttpStatus.OK.value());
    }
}
