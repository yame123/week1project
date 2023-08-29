package com.sparta.week1project.dto;

import com.sparta.week1project.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private String title;
    private String contents;
    private String username;
    private String time;

    public PostResponseDto(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
        this.username = postRequestDto.getUsername();
    }

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.username = post.getUsername();
        this.time = post.getTime();
    }

    public PostResponseDto(String title, String contents, String username, String time) {
        this.title = title;
        this.contents = contents;
        this.username = username;
        this.time = time;
    }
}
