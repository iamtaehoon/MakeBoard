package com.practice.board.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {
    private String comment;
    private Long postId;
}
