package com.week7.bannybannycarrotcarrot.dto;

import com.week7.bannybannycarrotcarrot.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailsDto implements UserDetails, OAuth2User {
    @Autowired
    private User user;
    private Map<String, Object> attributes;
    private String username;
    private String password;



    public UserDetailsDto(User user1) {
        this.setUsername(user1.getUsername());
        this.setPassword(user1.getPassword());
//        this.setAuthorities((List<GrantedAuthority>) user1);
        this.user = user1;

    }


    @Override
    public String getName() {
        return this.username;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
