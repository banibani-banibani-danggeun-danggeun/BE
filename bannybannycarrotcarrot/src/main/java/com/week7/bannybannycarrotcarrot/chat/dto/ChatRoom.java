package com.week7.bannybannycarrotcarrot.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Slf4j
public class ChatRoom {
    private Long roomId;
    private String postUserNickname;

    private boolean exist;

    public ChatRoom(Long roomId, String postUserNickname, boolean exist) {
        this.roomId = roomId;
        this.postUserNickname = postUserNickname;
        this.exist = exist;
    }



    /*public static ChatRoom create(String postUserNickname){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.postUserNickname = postUserNickname;
        return chatRoom;
    }*/

}
