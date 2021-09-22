package com.practice.board.service;

import com.practice.board.controller.CommentForm;
import com.practice.board.controller.PostForm;
import com.practice.board.domain.Comment;
import com.practice.board.domain.Member;
import com.practice.board.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class CommentServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void 댓글달기() throws Exception {
        //given
        Member member = new Member();
        member.makeMember("kimda1","123","김태훈","12@naver.com","010-1111-1111");
        Long memberId = memberService.join(member);

        PostForm postForm = new PostForm();
        postForm.setTitle("hi~");
        postForm.setContent("Hi my name is taehoonKim~~~~~~");
        Post post = Post.makePost(member,postForm);
        Post registerPost = postService.registerPost(post);

        CommentForm commentForm = new CommentForm();
        commentForm.setComment("댓글입니다1.");
        //when
        Long commentId = commentService.comment(memberId, registerPost.getId(), commentForm);

        //then
        Comment comment = commentService.findComment(commentId);
        assertThat(comment.getComment()).isEqualTo(commentForm.getComment());
    }

    @Test
    public void 게시물_댓글_전체확인() throws Exception {
        //given
        Member member = new Member();
        member.makeMember("kimda2","123","김태훈","12@naver.com","010-1111-1111");
        Long memberId = memberService.join(member);

        Member member2 = new Member();
        member2.makeMember("kimda22","123","김태훈","12@naver.com","010-1111-1111");
        Long memberId2 = memberService.join(member2);

        PostForm postForm = new PostForm();
        postForm.setTitle("hi댓글달아줘");
        postForm.setContent("hi댓글달아줘21₩3");
        Post post = Post.makePost(member,postForm);
        Post registerPost = postService.registerPost(post);

        PostForm postForm2 = new PostForm();
        postForm2.setTitle("hi댓글달아줘");
        postForm2.setContent("hi댓글달아줘21₩3");
        Post post2 = Post.makePost(member,postForm2);
        Post registerPost2 = postService.registerPost(post2);

        CommentForm commentForm1 = new CommentForm();
        commentForm1.setComment("댓글입니다1.");
        Long commentId1 = commentService.comment(memberId, registerPost.getId(), commentForm1);

        CommentForm commentForm2 = new CommentForm();
        commentForm2.setComment("다른 글에 달린 댓글입니다2.");
        Long commentId2 = commentService.comment(memberId2, registerPost2.getId(), commentForm2);

        CommentForm commentForm3 = new CommentForm();
        commentForm3.setComment("댓글입니다3.");
        Long commentId3 = commentService.comment(memberId, registerPost.getId(), commentForm3);

        //when
        List<Comment> comments = commentService.findComments(registerPost.getId());

        //then
        assertThat(comments.size()).isEqualTo(2);
//        for (Comment comment : comments) {
//            System.out.println("comment.getComment() = " + comment.getComment());
//        }
    }

    @Test
    public void 댓글_삭제() throws Exception {
        //given

        Member member = new Member();
        member.makeMember("kimda3","123","김태훈","12@naver.com","010-1111-1111");
        Long memberId = memberService.join(member);

        PostForm postForm = new PostForm();
        postForm.setTitle("hi~");
        postForm.setContent("Hi my name is taehoonKim~~~~~~");
        Post post = Post.makePost(member,postForm);
        Post registerPost = postService.registerPost(post);

        CommentForm commentForm = new CommentForm();
        commentForm.setComment("댓글입니다1.");

        Long commentId = commentService.comment(memberId, registerPost.getId(), commentForm);

        //when
        commentService.cancelComment(commentId);

        //then
        assertThrows(NullPointerException.class, () ->
                commentService.findComment(commentId).getComment());

    }

    @Test
    public void 댓글_수정() throws Exception {
        //given

        Member member = new Member();
        member.makeMember("kimda4","123","김태훈","12@naver.com","010-1111-1111");
        Long memberId = memberService.join(member);

        PostForm postForm = new PostForm();
        postForm.setTitle("hi~");
        postForm.setContent("Hi my name is taehoonKim~~~~~~");
        Post post = Post.makePost(member,postForm);
        Post registerPost = postService.registerPost(post);

        CommentForm commentForm = new CommentForm();
        commentForm.setComment("댓글입니다1.");
        Comment comment = Comment.makeComment(commentForm, member, post);
        Long commentId = commentService.comment(memberId, registerPost.getId(), commentForm);

        //when
        commentForm.setComment("댓글 변경");
        comment.modifyComment(commentForm);
        commentService.modifyComment(commentId, commentForm);

        //then
        assertThat(commentService.findComment(commentId).getComment()).isEqualTo(commentForm.getComment());
        System.out.println("commentService.findComment(commentId).getComment( = " + commentService.findComment(commentId).getComment());
    }
}