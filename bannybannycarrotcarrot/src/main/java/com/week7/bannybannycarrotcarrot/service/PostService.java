package com.week7.bannybannycarrotcarrot.service;

import com.week7.bannybannycarrotcarrot.dto.MsgDto;
import com.week7.bannybannycarrotcarrot.dto.PostDto;
import com.week7.bannybannycarrotcarrot.entity.Post;
import com.week7.bannybannycarrotcarrot.entity.User;
import com.week7.bannybannycarrotcarrot.errorcode.CommonStatusCode;
import com.week7.bannybannycarrotcarrot.errorcode.PostStatusCode;
import com.week7.bannybannycarrotcarrot.exception.RestApiException;
import com.week7.bannybannycarrotcarrot.repository.PostRepository;
import com.week7.bannybannycarrotcarrot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @Transactional
    public MsgDto.ResponseDto post(PostDto.PostRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RestApiException(CommonStatusCode.NOT_FIND_USER)
        );

        Post post = new Post(requestDto, user.getUsername());
        System.out.println("-------------------------------------");
        postRepository.save(post);
        return new MsgDto.ResponseDto(PostStatusCode.CREATE_POST);
    }


    @Transactional(readOnly = true)
    public List<PostDto.PostResponseDto> getPosts() {
        List<Post> posts = postRepository.findAllByOrderByIdDesc();
        return posts.stream().map(p -> new PostDto.PostResponseDto(p)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostDto.PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(CommonStatusCode.NOT_FIND_POST)
        );
        return new PostDto.PostResponseDto(post);
    }

    @Transactional
    public MsgDto.DataResponseDto updatePost(Long postId, PostDto.PostRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RestApiException(CommonStatusCode.NOT_FIND_USER)
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(CommonStatusCode.NOT_FIND_POST)
        );
        if(!post.getUsername().equals(user.getUsername())){
            throw new RestApiException(PostStatusCode.INVALID_USER_UPDATE);
        }
        post.update(requestDto);
        return new MsgDto.DataResponseDto(PostStatusCode.UPDATE_POST ,new PostDto.PostResponseDto(post));
    }

    @Transactional
    public MsgDto.ResponseDto deletePost(Long postId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RestApiException(CommonStatusCode.NOT_FIND_USER)
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(CommonStatusCode.NOT_FIND_POST)
        );
        if(!post.getUsername().equals(user.getUsername())){
            throw new RestApiException(PostStatusCode.INVALID_USER_DELETE);
        }
        postRepository.deleteById(postId);
        return new MsgDto.ResponseDto(PostStatusCode.DELETE_POST);
    }
}
