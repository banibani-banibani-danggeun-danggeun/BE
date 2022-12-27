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
    private String roomId;
    private String postUserNickname;

    public static ChatRoom create(String postUserNickname){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.postUserNickname = postUserNickname;
        return chatRoom;
    }
}
