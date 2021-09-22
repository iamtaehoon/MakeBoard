package com.practice.board.service;

import com.practice.board.controller.CommentForm;
import com.practice.board.domain.Comment;
import com.practice.board.domain.Member;
import com.practice.board.domain.Post;
import com.practice.board.repository.CommentRepository;
import com.practice.board.repository.MemberRepository;
import com.practice.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    /**
     * 댓글 등록
     */
    public Long comment(Long memberId, Long postId, CommentForm commentForm) {
        //엔티티 조회
        Member member = memberRepository.findMemberById(memberId);
        Post post = postRepository.findPost(postId);

        Comment comment = Comment.makeComment(commentForm, member, post);
        commentRepository.saveComment(comment);

        return comment.getId();
    }

    /**
     * 댓글 삭제
     */
    public void cancelComment(Long commentId) {
        commentRepository.deleteComment(commentId);
    }

    /**
     * 댓글 수정
     */
    public void modifyComment(Long commentId, CommentForm commentForm) {
        Comment comment = commentRepository.findComment(commentId);
        comment.modifyComment(commentForm);
        commentRepository.modifyComment(comment);
    }

    /**
     * 게시글 댓글 보기 기능
     */
    public List<Comment> findComments(Long postId){
        return commentRepository.findCommentsAboutPost(postId);
    }

    /**
     * 댓글 한개 찾기
     */
    public Comment findComment(Long commentId) {
        return commentRepository.findComment(commentId);
    }


}
