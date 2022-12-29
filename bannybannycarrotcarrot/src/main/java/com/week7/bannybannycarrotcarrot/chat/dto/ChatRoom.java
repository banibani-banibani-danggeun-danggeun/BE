package com.week7.bannybannycarrotcarrot.chat.dto;

import com.week7.bannybannycarrotcarrot.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;

@Getter
@Slf4j
@Builder
public class ChatRoom {
    private Long roomId;
    private String postUserNickname;

    private String title;

    private String content;

    private String image;

    private int price;

    private String location;

    public ChatRoom(Long roomId, Post post) {
        this.roomId = roomId;
        this.postUserNickname = post.getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.image = post.getImage();
        this.price = post.getPrice();
        this.location = post.getLocation();
    }



    /*public static ChatRoom create(String postUserNickname){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.postUserNickname = postUserNickname;
        return chatRoom;
    }*/

}
