package com.week7.bannybannycarrotcarrot.entity;

import jdk.jfr.Timespan;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;

@Entity
@NoArgsConstructor
@Getter
public class RoomDetail extends TimeStamped {

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
