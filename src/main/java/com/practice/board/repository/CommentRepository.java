package com.practice.board.repository;

import com.practice.board.domain.Comment;
import com.practice.board.domain.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CommentRepository {
    @PersistenceContext
    private EntityManager em;

    /**
     * 댓글 저장
      */
    public void saveComment(Comment comment){
        em.persist(comment);
    }


    /**
     * 댓글 수정
     */
    public void modifyComment(Comment comment){
        em.merge(comment); //merge쓰면 안댐. 풀어서 써야함. 일단 merge로 테스트해보자
        //제목만 바꾸거나, 내용만 바꿔도 모든 내용이 전부 바뀌어버림
    }

    /**
     * 댓글 1개 조회
     */
    public Comment findComment(Long commentId) {
        return em.find(Comment.class, commentId);
    }


    /**
     * 댓글 삭제
     */
    public void deleteComment(Long commentId) {
        Comment comment = findComment(commentId);
        em.remove(comment);
    }

    /**
     * 댓글 조건 맞춰 조회(멤버보고)
     */
    public List<Comment> findCommentsAboutPost(Long postId){
        return em.createQuery("select c from Comment c inner join c.post p where p.id = :postId ", Comment.class)
                .setParameter("postId",postId)
                .getResultList();
    }

}
