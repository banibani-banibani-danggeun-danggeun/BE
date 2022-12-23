package com.week7.bannybannycarrotcarrot.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private UserRole userRole;

    private Long kakaoId;        // 카카오 OAuth 를 위해 추가 -종열


    @Builder
    public User(String username, String password, Long kakaoId) {    // 카카오 OAuth 를 위해  kakaoId 추가 -종열
        this.username = username;
        this.password = password;
        this.userRole = UserRole.USER;
        this.kakaoId = kakaoId;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }

}
