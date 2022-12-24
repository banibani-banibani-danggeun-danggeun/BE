package com.week7.bannybannycarrotcarrot.controller;

import com.week7.bannybannycarrotcarrot.dto.MsgDto;
import com.week7.bannybannycarrotcarrot.dto.PostDto;
import com.week7.bannybannycarrotcarrot.security.UserDetailsImpl;
import com.week7.bannybannycarrotcarrot.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public MsgDto.ResponseDto post(@RequestBody PostDto.PostRequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails){
        log.info(userDetails.getUsername());
        return postService.post(requestDto, Long.parseLong(userDetails.getUsername()));
    }

    @GetMapping("/post")
    public List<PostDto.PostResponseDto> getposts(){
        return postService.getPosts();
    }
}
