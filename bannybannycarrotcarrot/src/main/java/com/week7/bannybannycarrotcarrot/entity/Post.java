package com.week7.bannybannycarrotcarrot.entity;

import com.week7.bannybannycarrotcarrot.chat.entity.Room;
import com.week7.bannybannycarrotcarrot.dto.PostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Post extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String image;

    @Column
    private int price;

    @Column
    private String location;

    @Column
    private String nickname;

/*    @OneToMany(mappedBy = "post")
    private List<Room> rooms = new ArrayList<>();*/

    public Post(PostDto.PostRequestDto requestDto, String nickname) {
        this.title = requestDto.title();
        this.content = requestDto.content();
        this.image = requestDto.image();
        this.price = requestDto.price();
        this.location = requestDto.location();
        this.nickname = nickname;
    }

    public void update(PostDto.PostRequestDto requestDto) {
        this.title = requestDto.title();
        this.content = requestDto.content();
        this.image = requestDto.image();
        this.price = requestDto.price();
        this.location = requestDto.location();
    }
}
