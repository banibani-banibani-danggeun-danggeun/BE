package com.week7.bannybannycarrotcarrot.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class RoomDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Room room;

    @Column(nullable = false)
    private String nickname;

    public RoomDetail(String nickname, Room room) {
        this.nickname = nickname;
        this.room = room;
    }
}
