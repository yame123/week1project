package com.sparta.week1project.repository;

import com.sparta.week1project.dto.PostRequestDto;
import com.sparta.week1project.dto.PostResponseDto;
import com.sparta.week1project.entity.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PostRepository {
    private final JdbcTemplate jdbcTemplate;
    public PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Post posting(Post post) {
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체
        String sql = "INSERT INTO post (title, contents, username, password, time) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, post.getTitle());
                    preparedStatement.setString(2, post.getContents());
                    preparedStatement.setString(3, post.getUsername());
                    preparedStatement.setString(4, post.getPassword());
                    preparedStatement.setString(5, post.getTime());
                    return preparedStatement;
                },
                keyHolder);
        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        post.setId(id);
        return post;
    }

    public List<PostResponseDto> getPosts() {
        String sql = "SELECT * FROM post";
        return jdbcTemplate.query(sql, new RowMapper<PostResponseDto>() {
            @Override
            public PostResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 post 데이터들을 postResponseDto 타입으로 변환해줄 메서드
                String title = rs.getString("title");
                String username = rs.getString("username");
                String contents = rs.getString("contents");
                String time = rs.getString("time");
                return new PostResponseDto(title, contents, username, time);
            }
        });
    }


    public PostResponseDto getOnePost(Long id) {
        String sql = "SELECT * FROM post WHERE id = ?";
        return jdbcTemplate.query(sql, new RowMapper<PostResponseDto>() {
            @Override
            public PostResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 post 데이터들을 postResponseDto 타입으로 변환해줄 메서드
                String title = rs.getString("title");
                String username = rs.getString("username");
                String contents = rs.getString("contents");
                String time = rs.getString("time");
                return new PostResponseDto(title, contents, username, time);
            }
        },id).remove(0);
    }


    public void updatePost(Long id, PostRequestDto postRequestDto) {
        String sql = "UPDATE post SET title = ?, contents = ? WHERE id = ?";
        jdbcTemplate.update(sql, postRequestDto.getTitle(), postRequestDto.getContents(), id);
    }


    public void deletePost(Long id, PostRequestDto postRequestDto) {
        String sql = "DELETE FROM post WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    public Post findById(Long id) {
        // DB 조회
        String sql = "SELECT * FROM post WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Post post = new Post();
                post.setUsername(resultSet.getString("username"));
                post.setContents(resultSet.getString("contents"));
                post.setId(resultSet.getLong("id"));
                post.setTitle(resultSet.getString("title"));
                post.setPassword(resultSet.getString("password"));
                post.setTime(resultSet.getString("time"));
                return post;
            } else {
                return null;
            }
        }, id);
    }
}

