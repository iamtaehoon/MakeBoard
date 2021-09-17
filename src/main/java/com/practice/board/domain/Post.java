package com.practice.board.domain;

import com.practice.board.controller.PostForm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // lazy 걸어줘야
    @JoinColumn(name = "member_id") // member_id란 이름을 생성하지만, 다른 테이블에 있는 저 이름의 그걸 갖고오는건가?
    private Member member;

    private String title;
    private String content; // String해도 되나?

    //    private Lob attachments; // 음.. 이거 일단 보류 //첨부파일


    //setter 만드는게 맞는건가... id를 유지하면서 title이나 content 바꾸려면 어떻게 해야하지.
    public void modifyPost(PostForm postForm) {
        this.title = postForm.getTitle();
        this.content = postForm.getContent();
    }


    public static Post makePost(Member member,PostForm postForm) {
        Post post = new Post();
        post.member = member;
        post.title = postForm.getTitle();
        post.content = postForm.getContent();

        return post;
    }
}
