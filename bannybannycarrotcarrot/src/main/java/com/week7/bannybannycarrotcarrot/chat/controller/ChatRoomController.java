package com.week7.bannybannycarrotcarrot.chat.controller;

import com.week7.bannybannycarrotcarrot.chat.dto.ChatRoom;
import com.week7.bannybannycarrotcarrot.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 채팅방 생성
    @PostMapping("/room")
    public ChatRoom createRoom(@RequestParam Long postId) {
        return chatRoomService.createChatRoom(postId);
    }
}
