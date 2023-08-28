package com.sparta.week1project.service;

import com.sparta.week1project.dto.PostRequestDto;
import com.sparta.week1project.dto.PostResponseDto;
import com.sparta.week1project.entity.Post;
import com.sparta.week1project.repository.PostRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PostService {
    private final JdbcTemplate jdbcTemplate;

    public PostService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PostResponseDto posting(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto);
        post.timeset();

        PostRepository postRepository = new PostRepository(jdbcTemplate);
        Post savePost = postRepository.posting(post);

        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;
    }

    public List<PostResponseDto> getPosts() {
        PostRepository postRepository = new PostRepository(jdbcTemplate);
        return postRepository.getPosts();
    }

    public PostResponseDto getOnePost(Long id) {
        PostRepository postRepository = new PostRepository(jdbcTemplate);
        return postRepository.getOnePost(id);
    }

    public Long updatePost(Long id, PostRequestDto postRequestDto) {
        PostRepository postRepository = new PostRepository(jdbcTemplate);
        Post post = postRepository.findById(id);
        // 해당 메모가 DB에 존재하는지 확인
        if (post != null) {
            if(post.getPassword().equals(postRequestDto.getPassword())){
                postRepository.updatePost(id,postRequestDto);
                return id;
            }else{
                throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
            }
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }



    public Long deletePost(Long id, PostRequestDto postRequestDto) {
        PostRepository postRepository = new PostRepository(jdbcTemplate);
        Post post = postRepository.findById(id);
        if (post != null) {
            if(post.getPassword().equals(postRequestDto.getPassword())){
                postRepository.deletePost(id,postRequestDto);
                return id;
            }else{
                throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
            }
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }
}
