package com.practice.board.service;

import com.practice.board.controller.PostForm;
import com.practice.board.domain.Post;
import com.practice.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    /**
     * 게시물 등록
     */
    public Post registerPost(Post post) {
        //검증 필요없음
        postRepository.savePost(post);

        return post;
    }

    /**
     * 게시물 조회
     */
    public Post findPost(Long postId){
        return postRepository.findPost(postId);
    }

    /**
     * 게시물 수정
     */
    public Post modifyPost(Long postId) {
        Post post = findPost(postId);
        postRepository.modifyPost(post);

        return post;
    }

    /**
     * 게시물 삭제
     */
    public void deletePost(Long postId) {
        postRepository.deletePost(postId);
    }

    /**
     * 게시물 페이징 조회
     */
    public List<Post> findPosts() {
        return postRepository.findAllPost();
    }

    /**
     * 게시물 검색
     */


}
