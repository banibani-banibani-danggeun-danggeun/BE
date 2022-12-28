package com.week7.bannybannycarrotcarrot.oauth2;

import com.week7.bannybannycarrotcarrot.dto.UserDetailsDto;
import com.week7.bannybannycarrotcarrot.entity.User;
import com.week7.bannybannycarrotcarrot.repository.UserRepository;
import com.week7.bannybannycarrotcarrot.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {


    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final HttpServletResponse httpServletResponse;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println(userRequest.getAccessToken());

        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User1 = delegate.loadUser(userRequest);

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = "google"; //userRequest.getClientRegistration().getClientId();//google
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider + "_" + providerId;
        String password1 = "carrot";
        String password = passwordEncoder.encode(password1);
        String nickname = provider + "재하";



        User user = userRepository.findByUsername(username).orElse(new User());
        System.out.println(user.getUsername());
        System.out.println("---------------");
        if(user.getUsername() == null) {
            log.info("최초로그인시회원가입진행 --------------------");
            user = User.builder()
                    .username(username)
                    .password(password)
                    .provider(provider)
                    .providerId(providerId)
                    .nickname(nickname)
                    .build();
            userRepository.save(user);
        }else{
            log.info("최초로그인이아님 --------------------");
        }


        UsernamePasswordAuthenticationToken beforeAuthentication = new UsernamePasswordAuthenticationToken(username, password1);
        Authentication afterAuthentication = authenticationManagerBuilder.getObject().authenticate(beforeAuthentication);
        System.out.println(jwtUtil.generateToken(afterAuthentication));
        httpServletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.generateToken(afterAuthentication));



        return oAuth2User1;
    }

}
