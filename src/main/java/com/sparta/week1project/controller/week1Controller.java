package com.sparta.week1project.controller;

import com.sparta.week1project.dto.PostRequestDto;
import com.sparta.week1project.dto.PostResponseDto;
import com.sparta.week1project.service.PostService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class week1Controller {
    private final JdbcTemplate jdbcTemplate;

    public week1Controller(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @GetMapping("/post")//모든 포스팅
    public List<PostResponseDto> getPosts(){
        PostService postService = new PostService(jdbcTemplate);
        return postService.getPosts();
    }


    @GetMapping("/post/{id}")//한개 포스팅
    public PostResponseDto getOnePost(@PathVariable Long id){
        PostService postService = new PostService(jdbcTemplate);
        return postService.getOnePost(id);
    }

    @PostMapping("/post")//포스팅 등록
    public PostResponseDto posting(@RequestBody PostRequestDto postRequestDto){
        PostService postService = new PostService(jdbcTemplate);
        return postService.posting(postRequestDto);
    }

    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto){
        PostService postService = new PostService(jdbcTemplate);
        return postService.updatePost(id,postRequestDto);
    }

    @DeleteMapping("/post/{id}")
    public PostResponseDto deletePost(@PathVariable Long id,@RequestBody PostRequestDto postRequestDto){
        PostService postService = new PostService(jdbcTemplate);
        return postService.deletePost(id,postRequestDto);

    }


}
