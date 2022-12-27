package com.week7.bannybannycarrotcarrot.dto;

import com.week7.bannybannycarrotcarrot.entity.Post;

public class PostDto {

    public record PostRequestDto(String title,
                                 String content,
                                 String image,
                                 int price,
                                 String location
                                 ){

    }
    public record PostResponseDto(
            Long id,
            String nickname,
            String title,
            String content,
            String image,
            int price,
            String location) {
        public PostResponseDto(Post post) {
                this(post.getId(), post.getNickname(), post.getTitle(), post.getContent(), post.getImage(),
                    post.getPrice(), post.getLocation());
        }

    }



}
