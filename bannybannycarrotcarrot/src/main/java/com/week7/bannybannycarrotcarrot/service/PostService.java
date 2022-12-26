package com.week7.bannybannycarrotcarrot.service;

import com.week7.bannybannycarrotcarrot.dto.MsgDto;
import com.week7.bannybannycarrotcarrot.dto.PostDto;
import com.week7.bannybannycarrotcarrot.entity.Post;
import com.week7.bannybannycarrotcarrot.entity.User;
import com.week7.bannybannycarrotcarrot.repository.PostRepository;
import com.week7.bannybannycarrotcarrot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<?> post(PostDto.PostRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("회원을 찾을 수 없습니다.")
        );

        Post post = new Post(requestDto, user.getNickname());
        System.out.println("-------------------------------------");
        postRepository.save(post);
//        return new MsgDto.ResponseDto("게시글작성", HttpStatus.OK.value());
        return ResponseEntity.ok(new MsgDto.DataResponseDto("게시글 작성 완료", HttpStatus.OK.value(), new PostDto.PostResponseDto(post)));
    }


    @Transactional(readOnly = true)
    public List<PostDto.PostResponseDto> getPosts() {
        List<Post> posts = postRepository.findAllByOrderByIdDesc();
        return posts.stream().map(p -> new PostDto.PostResponseDto(p)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostDto.PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("없는글번호입니다.")
        );
        return new PostDto.PostResponseDto(post);
    }

    @Transactional
    public ResponseEntity<?> updatePost(Long postId, PostDto.PostRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("회원을 찾을 수 없습니다.")
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );
        if(!post.getNickname().equals(user.getNickname())){
            return ResponseEntity.ok(new MsgDto.ResponseDto("작성자만 수정할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
        post.update(requestDto);
        return ResponseEntity.ok(new MsgDto.DataResponseDto("게시글 수정 완료", HttpStatus.OK.value(), new PostDto.PostResponseDto(post)));
    }

    @Transactional
    public MsgDto.ResponseDto deletePost(Long postId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("회원을 찾을 수 없습니다.")
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );
        if(!post.getNickname().equals(user.getNickname())){
            return new MsgDto.ResponseDto("작성자만 삭제할 수 있습니다.", HttpStatus.BAD_REQUEST.value());
        }
        postRepository.deleteById(postId);
        return new MsgDto.ResponseDto("게시물 삭제 완료.", HttpStatus.OK.value());
    }
}
