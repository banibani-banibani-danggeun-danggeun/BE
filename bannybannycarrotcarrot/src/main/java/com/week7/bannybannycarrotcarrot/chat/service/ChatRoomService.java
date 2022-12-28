package com.week7.bannybannycarrotcarrot.chat.service;

import com.week7.bannybannycarrotcarrot.chat.dto.ChatRoom;
import com.week7.bannybannycarrotcarrot.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final PostRepository postRepository;
    private Map<String, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    public ChatRoom createChatRoom(Long postId) {
        String postUserNickname = postRepository.getReferenceById(postId).getNickname();
        ChatRoom chatRoom = ChatRoom.create(postUserNickname);
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);

        return chatRoom;
    }
}
