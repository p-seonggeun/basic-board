package com.example.basicboard.repository;

import com.example.basicboard.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class PostRepositoryTest {

    @Autowired
    private PostRepository repository;

    @Test
    public void 게시글_생성_조회() throws Exception {
        //given
        Post post = new Post("제목", "내용");
        //when
        repository.save(post);

        //then
        List<Post> result = repository.findAll();
        assertEquals(1, result.size());
        Post findPostById = repository.findById(1L).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        Post findPost = result.getFirst();
        assertEquals("제목", findPost.getTitle());
        assertEquals(findPost, findPostById);
    }

    @Test
    public void 게시글_수정() throws Exception {
        //given
        Post post = new Post("제목", "내용");
        repository.save(post);
        Long postId = post.getId();

        //when
        Post findPost = repository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        findPost.changePost("수정제목", "수정내용");

        //then
        repository.flush();
        Post updatedPost = repository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));

        assertEquals(updatedPost, findPost);
        assertEquals("수정제목", updatedPost.getTitle());
        assertEquals("수정내용", updatedPost.getContent());
    }


}