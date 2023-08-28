package com.sparta.week1project.controller;

import com.sparta.week1project.dto.PostRequestDto;
import com.sparta.week1project.dto.PostResponseDto;
import com.sparta.week1project.entity.Post;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class week1Controller {
    private final Map<Long, Post> postList = new HashMap<>();

    @GetMapping("/post")//모든 포스팅
    public List<PostResponseDto> getPosts(){
        if (postList.size()!=0){
            List<PostResponseDto> postResponseDto = new ArrayList<>(postList.values().stream().map(PostResponseDto::new).toList());
            postResponseDto.sort((a,b)->b.getTime().compareTo(a.getTime()));
            return postResponseDto;
        } else {
            throw new IllegalArgumentException("등록된 게시물이 없습니다.");
        }
    }

    @GetMapping("/post/{id}")//한개 포스팅
    public PostResponseDto getOnePost(@PathVariable Long id){
        if (postList.size()!=0){
            PostResponseDto postResponseDto = new PostResponseDto(postList.get(id));
            return postResponseDto;
        } else {
            throw new IllegalArgumentException("해당 게시물을 찾을 수 없습니다.");
        }
    }

    @PostMapping("/post")//포스팅 등록
    public PostResponseDto postResponseDto(@RequestBody PostRequestDto postRequestDto){
        Post post = new Post(postRequestDto);
        Long index = postList.size()>0?Collections.max(postList.keySet())+1:1;
        post.setId(index);
        post.timeset();
        postList.put(index,post);
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;
    }

    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto){
        if(postList.containsKey(id)){
            Post post = postList.get(id);
            if(post.getPassword().equals(postRequestDto.getPassword())){
                post.setTitle(postRequestDto.getTitle());
                post.setContents(postRequestDto.getContents());
                post.setUsername(postRequestDto.getUsername());
                postList.put(id,post);
                return new PostResponseDto(post);
            } else{
                throw new IllegalArgumentException("비밀번호가 다릅니다.");
            }
        }else{
            throw new IllegalArgumentException("해당 게시물을 찾을 수 없습니다.");
        }
    }

    @DeleteMapping("/post/{id}")
    public PostResponseDto deletePost(@PathVariable Long id,@RequestBody PostRequestDto postRequestDto){
        if(postList.containsKey(id)) {
            Post post = postList.get(id);
            if(post.getPassword().equals(postRequestDto.getPassword())){
                postList.remove(id);
                post.setTitle(post.getTitle()+" 게시물은 삭제되었습니다.");
                return new PostResponseDto(post);
            }else{
                throw new IllegalArgumentException("비밀번호가 다릅니다.");
            }
        }else{
            throw new IllegalArgumentException("해당 게시물을 찾을 수 없습니다.");
        }
    }
}
