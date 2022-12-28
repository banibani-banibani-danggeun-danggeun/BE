package com.week7.bannybannycarrotcarrot.chat.entity;

import com.week7.bannybannycarrotcarrot.chat.dto.ChatMessage;

import javax.persistence.*;

@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @Column(nullable = false)
    private ChatMessage.MessageType type; // 메시지 타입

    @Column(nullable = false)
    private String roomId; // 방번호

    @Column(nullable = false)
    private String sender; // 메시지 보낸사람

    @Column
    private String message; // 메시지

    public Chat(ChatMessage message) {
        this.type = message.getType();
        this.roomId = message.getRoomId();
        this.sender = message.getSender();
        this.message = message.getMessage();
    }
}
