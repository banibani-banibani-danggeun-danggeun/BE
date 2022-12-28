package com.week7.bannybannycarrotcarrot.chat.service;

import com.week7.bannybannycarrotcarrot.chat.dto.ChatDto;
import com.week7.bannybannycarrotcarrot.chat.dto.ChatMessage;
import com.week7.bannybannycarrotcarrot.entity.Chat;
import com.week7.bannybannycarrotcarrot.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public void save(ChatMessage message) {
        Chat chat = new Chat(message);
        chatRepository.save(chat);
    }

    public List<ChatDto> getMessage(Long roomId) {
        List<Chat> chatList = chatRepository.findAllByRoomIdOrderByCreateAtDesc(roomId);
        List<ChatDto> chatDtos = new ArrayList<>();

        for (Chat chat : chatList) {
            chatDtos.add(new ChatDto(chat.getMessage(), chat.getSender(), chat.getCreateAt()));
        }
        return chatDtos;
    }
}
