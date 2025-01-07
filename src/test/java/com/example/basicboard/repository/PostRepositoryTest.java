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
        Post findPostById = repository.findPostById(1L).orElse(null);
        Post findPost = result.getFirst();
        assertEquals("제목", findPost.getTitle());
        assertEquals(findPost, findPostById);
    }

}