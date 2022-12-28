package com.week7.bannybannycarrotcarrot.chat.repository;

import com.week7.bannybannycarrotcarrot.chat.entity.RoomDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomDetailRepository extends JpaRepository<RoomDetail, Long> {

    Optional<RoomDetail> findByNicknameAndRoomId(String nickname, Long roomId);
}
