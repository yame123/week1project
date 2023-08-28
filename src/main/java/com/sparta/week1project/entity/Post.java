package com.sparta.week1project.entity;


import com.sparta.week1project.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class Post {
    private String title;
    private String contents;
    private String password;
    private String username;
    private Long id;
    private String time;

    public Post(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
        this.password = postRequestDto.getPassword();
        this.username = postRequestDto.getUsername();
    }

    public void timeset(){
        this.time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));
    }

}
