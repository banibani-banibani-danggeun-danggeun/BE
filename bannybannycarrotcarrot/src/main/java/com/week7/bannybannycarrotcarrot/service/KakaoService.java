package com.week7.bannybannycarrotcarrot.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.week7.bannybannycarrotcarrot.dto.KakaoUserInfoDto;
import com.week7.bannybannycarrotcarrot.dto.MsgDto;
import com.week7.bannybannycarrotcarrot.dto.TokenDto;
import com.week7.bannybannycarrotcarrot.entity.RefreshToken;
import com.week7.bannybannycarrotcarrot.entity.User;
import com.week7.bannybannycarrotcarrot.entity.UserRole;
import com.week7.bannybannycarrotcarrot.errorcode.UserStatusCode;
import com.week7.bannybannycarrotcarrot.interfacepackage.Logininterface;
import com.week7.bannybannycarrotcarrot.repository.RefreshTokenRepository;
import com.week7.bannybannycarrotcarrot.repository.SocialAccessTokenRepository;
import com.week7.bannybannycarrotcarrot.repository.UserRepository;
import com.week7.bannybannycarrotcarrot.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService implements Logininterface {
    public final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    public   final UserRepository userRepository;
    public final JwtUtil jwtUtil;

    //  https://kauth.kakao.com/oauth/authorize?client_id={REST_API_KEY}&redirect_uri={REDIRECT_URI}&response_type=code

    //  https://kauth.kakao.com/oauth/authorize?client_id=d6c5b10cf544ae8fcc0cbb0bbc530328&redirect_uri=http://banibanipj.s3-website.ap-northeast-2.amazonaws.com/api/user/kakao/callback&response_type=code



    public MsgDto.DataResponseDto kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException {
        // 1. "?????? ??????"??? "????????? ??????" ??????
        String accessToken = getToken(code);

        // 2. ???????????? ????????? API ?????? : "????????? ??????"?????? "????????? ????????? ??????" ????????????
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

        // 3. ???????????? ????????????
        User kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);

//        forceLoginUser(kakaoUser);
//        createToken(kakaoUser, response);

        String Token = jwtUtil.createToken(kakaoUser.getId());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, Token);



//        UsernamePasswordAuthenticationToken beforeAuthentication = new UsernamePasswordAuthenticationToken(kakaoUserInfo.getEmail(),kakaoUser.getUserRole());
//
//        //?????? ????????? ?????? ??????
////        Authentication afterAuthentication = authenticationManagerBuilder.getObject().authenticate(beforeAuthentication);
//        String generateToken = jwtUtil.generateToken(beforeAuthentication);
//        //?????? ????????? ????????? JWT ??????
//        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, generateToken);



//         4. JWT ?????? ??????
//        String createToken =  jwtUtil.generateToken(kakaoUser.getUsername());
//        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, generateToken);
        return new MsgDto.DataResponseDto(UserStatusCode.USER_LOGIN_SUCCESS, kakaoUser.getNickname());
    }

    // 1. "?????? ??????"??? "????????? ??????" ??????
    private String getToken(String code) throws JsonProcessingException {
        // HTTP Header ??????
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body ??????
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "d6c5b10cf544ae8fcc0cbb0bbc530328");
        body.add("redirect_uri", "http://banibanipj.s3-website.ap-northeast-2.amazonaws.com/api/user/kakao/callback");
        body.add("code", code);

        // HTTP ?????? ?????????
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP ?????? (JSON) -> ????????? ?????? ??????
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    // 2. ???????????? ????????? API ?????? : "????????? ??????"?????? "????????? ????????? ??????" ????????????
    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header ??????
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP ?????? ?????????
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        log.info("????????? ????????? ??????: " + id + ", " + nickname + ", " + email);
        return new KakaoUserInfoDto(id, nickname, email);
    }

    // 3. ???????????? ????????????
    private User registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        // DB ??? ????????? Kakao Id ??? ????????? ??????
        Long kakaoId = kakaoUserInfo.getId();
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(new User());
        if (kakaoUser.getKakaoId() == null) {
                // ?????? ????????????
                // password: random UUID
                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);

                // email: kakao email
                String email = kakaoUserInfo.getEmail();
                kakaoUser = new User(kakaoUserInfo.getNicknmae(), kakaoId, encodedPassword, email, UserRole.USER);
                userRepository.save(kakaoUser);
        }
        return kakaoUser;
    }

//    public void createToken(User user, HttpServletResponse response) {
//        TokenDto tokenDto = jwtUtil.createAllToken(user.getEmail());
//
//        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByAccountEmail(user.getEmail());
//
//        if (refreshToken.isPresent()) {
//            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
//        } else {
//            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), user.getEmail());
//            refreshTokenRepository.save(newToken);
//        }
//
//        setHeader(response, tokenDto);
//    }





}
