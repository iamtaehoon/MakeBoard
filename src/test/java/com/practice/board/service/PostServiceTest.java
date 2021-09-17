package com.practice.board.service;

import com.practice.board.controller.PostForm;
import com.practice.board.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    public void 게시물_등록() throws Exception {
        //given
        PostForm postForm = new PostForm();
        postForm.setTitle("hi~");
        postForm.setContent("Hi my name is taehoonKim~~~~~~");
        Post post = Post.makePost(postForm);

        //when
        Post registerPost = postService.registerPost(post);

        //then
        Assertions.assertThat(registerPost.getTitle()).isEqualTo(postForm.getTitle());
        Assertions.assertThat(registerPost.getContent()).isEqualTo(postForm.getContent());
    }

    @Test
    public void 게시물_수정_내용변경() throws Exception {
        //given
        PostForm postForm = new PostForm();
        postForm.setTitle("hi~");
        postForm.setContent("Hi my name is taehoonKim~~~~~~");

        Post post = Post.makePost(postForm);
        postService.registerPost(post);

        //when
        postForm.setContent("내용을 바꿔치기했습니다");
        post.modifyPost(postForm);
        Post modifyPost = postService.modifyPost(post.getId());
        //then

        Assertions.assertThat(modifyPost.getTitle()).isEqualTo(postForm.getTitle());
        Assertions.assertThat(modifyPost.getContent()).isEqualTo(modifyPost.getContent());
        Assertions.assertThat(post.getId()).isEqualTo(modifyPost.getId());
    }

    @Test
    public void 게시물_삭제() throws Exception {
        //given
        PostForm postForm = new PostForm();
        postForm.setTitle("hi~");
        postForm.setContent("Hi my name is taehoonKim~~~~~~");

        Post post = Post.makePost(postForm);
        Post registerPost = postService.registerPost(post);


        //when
        postService.deletePost(registerPost.getId());

        //then

        org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class,()->{
            postService.findPost(registerPost.getId()).getTitle();
        });
    }
}