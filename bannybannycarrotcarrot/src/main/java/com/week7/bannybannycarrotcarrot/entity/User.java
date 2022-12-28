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

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private UserRole userRole;

    @Column(nullable = false)
    private  String nickname;

    private String provider;//google, Kakao등등이들어감

    private String providerId;



    @Builder
    public User(String username, String password, String provider, String providerId, String nickname){
        this.username = username;
        this.password = password;
        this.userRole = UserRole.USER;
        this.provider = provider;
        this.providerId = providerId;
        this.nickname = nickname;
    }


    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }


}
