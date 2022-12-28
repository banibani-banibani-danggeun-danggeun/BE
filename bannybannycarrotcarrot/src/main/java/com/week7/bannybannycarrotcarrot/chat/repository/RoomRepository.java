package com.week7.bannybannycarrotcarrot.chat.repository;

import com.week7.bannybannycarrotcarrot.chat.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room,Long> {
    List<Room> findByPostId(Long postId);
}
