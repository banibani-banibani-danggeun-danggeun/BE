package com.week7.bannybannycarrotcarrot.controller;

import com.week7.bannybannycarrotcarrot.dto.MsgDto;
import com.week7.bannybannycarrotcarrot.dto.UserDto;
import com.week7.bannybannycarrotcarrot.oauth2.PrincipalOauth2UserService;
import com.week7.bannybannycarrotcarrot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.web.server.AuthenticatedPrincipalServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final PrincipalOauth2UserService principalOauth2UserService;


    @PostMapping("/signup")
    public MsgDto.ResponseDto signup(@RequestBody @Valid UserDto.SignupRequestDto requestDto) {
        return userService.signup(requestDto);
    }

    @PostMapping("/login")
    public MsgDto.DataResponseDto login(@RequestBody UserDto.LoginRequestDto requestDto, HttpServletResponse httpServletResponse) {
        return userService.login(requestDto, httpServletResponse);
    }

    @GetMapping("idcheck/{username}")
    public MsgDto.ResponseDto idCheck(@PathVariable String username) {

        return userService.idCheck(username);
    }

//    @GetMapping("")
//    public void googleLogin(HttpServletResponse httpServletResponse){
//        principalOauth2UserService.loadUser(httpServletResponse);
//    }

}
