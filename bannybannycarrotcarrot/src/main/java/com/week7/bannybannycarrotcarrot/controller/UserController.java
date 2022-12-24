package com.week7.bannybannycarrotcarrot.controller;

import com.week7.bannybannycarrotcarrot.dto.MsgDto;
import com.week7.bannybannycarrotcarrot.dto.UserDto;
import com.week7.bannybannycarrotcarrot.entity.User;
import com.week7.bannybannycarrotcarrot.errorcode.UserStatusCode;
import com.week7.bannybannycarrotcarrot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<MsgDto.ResponseDto> signup(@RequestBody @Valid UserDto.SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return new ResponseEntity<>(new MsgDto.ResponseDto(UserStatusCode.USER_SIGNUP_SUCCESS), HttpStatus.OK);
    }

    @PostMapping("/login")
    public MsgDto.ResponseDto login(@RequestBody UserDto.LoginRequestDto requestDto, HttpServletResponse httpServletResponse) {
        return userService.login(requestDto, httpServletResponse);
    }

    @GetMapping("idcheck/{username}")
    public MsgDto.ResponseDto idCheck(@PathVariable String username) {

        return userService.idCheck(username);
    }
}
