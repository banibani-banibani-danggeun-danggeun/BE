package com.week7.bannybannycarrotcarrot.chat.service;

import com.week7.bannybannycarrotcarrot.chat.dto.ChatMessage;
import com.week7.bannybannycarrotcarrot.chat.entity.Chat;
import com.week7.bannybannycarrotcarrot.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public void save(ChatMessage message) {
        Chat chat = new Chat(message);
        chatRepository.save(chat);
    }
}
