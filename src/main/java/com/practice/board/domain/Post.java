package com.practice.board.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Post {
    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // lazy 걸어줘야
    @JoinColumn(name = "member_id") // member_id란 이름을 생성하지만, 다른 테이블에 있는 저 이름의 그걸 갖고오는건가?
    private Member member;

    private String title;
    private String contents; // String해도 되나?

//    private Lob attachments; // 음.. 이거 일단 보류 //첨부파일

}
