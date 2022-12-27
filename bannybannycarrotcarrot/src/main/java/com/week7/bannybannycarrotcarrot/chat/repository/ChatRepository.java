package com.week7.bannybannycarrotcarrot.chat.repository;

import com.week7.bannybannycarrotcarrot.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
