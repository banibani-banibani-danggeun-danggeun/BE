package com.week7.bannybannycarrotcarrot.chat.repository;

import com.week7.bannybannycarrotcarrot.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByRoomIdOrderByCreateAtDesc(Long roomId);
}
