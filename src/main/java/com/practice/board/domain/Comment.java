package com.practice.board.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String comment; // 댓글 내용을 말하는거
}
