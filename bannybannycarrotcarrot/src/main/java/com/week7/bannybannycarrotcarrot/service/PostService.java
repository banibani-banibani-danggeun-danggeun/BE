package com.week7.bannybannycarrotcarrot.service;

import com.week7.bannybannycarrotcarrot.dto.MsgDto;
import com.week7.bannybannycarrotcarrot.dto.PostDto;
import com.week7.bannybannycarrotcarrot.entity.Post;
import com.week7.bannybannycarrotcarrot.entity.User;
import com.week7.bannybannycarrotcarrot.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    @Transactional
    public MsgDto.ResponseDto post(PostDto.PostRequestDto requestDto, User user) {
        Post post = new Post(requestDto, user.getUsername());
        System.out.println("-------------------------------------");
        postRepository.save(post);
        return new MsgDto.ResponseDto("게시글작성", HttpStatus.OK.value());
    }


    @Transactional(readOnly = true)
    public List<PostDto.PostResponseDto> getPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreateAtDesc();
        return posts.stream().map(p -> new PostDto.PostResponseDto(p)).collect(Collectors.toList());
    }
}
