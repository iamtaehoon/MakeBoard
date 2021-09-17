package com.practice.board.repository;

import com.practice.board.domain.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PostRepository {
    @PersistenceContext
    private EntityManager em;

    /**
     * 게시물 저장
     */
    public void savePost(Post post){
        em.persist(post);
    }

    /**
     * 게시물 조회
     */
    public Post findPost(Long postId){
        return em.find(Post.class, postId);
    }

    /**
     * 게시물 전체 조회(전체조회가 조건에 맞는거 조회를 만들어야하는데, 이거 querydsl써서 만들어야함. 내가 아직 할수 없음)
     * 페이징도 여기에 들어갈 것으로 예상.
     */
    public List<Post> findAllPost() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    /**
     * 게시물 수정
     */
    public void modifyPost(Post post){
        em.merge(post); //merge쓰면 안댐. 풀어서 써야함. 일단 merge로 테스트해보자
        //제목만 바꾸거나, 내용만 바꿔도 모든 내용이 전부 바뀌어버림
    }

    /**
     * 게시물 삭제
     */
    public void deletePost(Long postId) {

        Post post = findPost(postId);
        em.remove(post);
    }

    /**
     * 파일 첨부
     */



}
