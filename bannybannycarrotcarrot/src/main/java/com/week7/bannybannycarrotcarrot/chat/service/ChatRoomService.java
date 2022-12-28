package com.week7.bannybannycarrotcarrot.chat.service;

import com.week7.bannybannycarrotcarrot.chat.dto.ChatRoom;
import com.week7.bannybannycarrotcarrot.entity.Room;
import com.week7.bannybannycarrotcarrot.entity.RoomDetail;
import com.week7.bannybannycarrotcarrot.repository.RoomDetailRepository;
import com.week7.bannybannycarrotcarrot.repository.RoomRepository;
import com.week7.bannybannycarrotcarrot.entity.Post;
import com.week7.bannybannycarrotcarrot.repository.PostRepository;
import com.week7.bannybannycarrotcarrot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final PostRepository postRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final RoomDetailRepository roomDetailRepository;

    public ChatRoom createChatRoom(Long postId, String userNickname) {
        Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
        List<Room> roomList = roomRepository.findByPostId(postId);
        for (Room room : roomList) {
            List<RoomDetail> details = room.getRoomDetails();
            for (RoomDetail detail : details) {
                if (detail.getNickname().equals(userNickname)) {
                    //프론트 ->
                    return new ChatRoom(room.getId(), post.getNickname(), true);
                }
            }
        }

        /*Optional<User> loginUser = userRepository.findByUsername(userNickname);
        Optional<User> postUser = userRepository.findByUsername(post.getNickname());
        if (loginUser.isPresent() && postUser.isPresent()) {

        }*/

        Room room = Room.builder().post(post).build();

        room.getRoomDetails().add(new RoomDetail(post.getNickname(), room));
        room.getRoomDetails().add(new RoomDetail(userNickname, room));

        Room room1 = roomRepository.save(room);

        return new ChatRoom(room1.getId(), post.getNickname(), false);
    }
}
