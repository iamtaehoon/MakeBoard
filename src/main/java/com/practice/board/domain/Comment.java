package com.practice.board.domain;

import com.practice.board.controller.CommentForm;
import com.practice.board.controller.PostForm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String comment; // 댓글 내용을 말하는거

    public void modifyComment(CommentForm commentForm) {
        this.comment = commentForm.getComment();
    }

    public static Comment makeComment(CommentForm commentForm, Member member, Post post) {
        Comment comment = new Comment();
        comment.post = post;
        comment.member = member;
        comment.comment = commentForm.getComment();

        return comment;
    }
}
