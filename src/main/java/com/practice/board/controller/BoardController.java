package com.practice.board.controller;

import com.practice.board.domain.Comment;
import com.practice.board.domain.Member;
import com.practice.board.domain.Post;
import com.practice.board.service.CommentService;
import com.practice.board.service.MemberService;
import com.practice.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final PostService postService;
    private final MemberService memberService;
    private final CommentService commentService;

    @GetMapping("/")
    public String mainPage(Model model) {

        System.out.println("mainPage 들어옴.");
        List<Post> posts = postService.findPosts();
        model.addAttribute("posts", posts);

        return "board";
    }

    @GetMapping("/login")
    public String login(Model model) {

        //임시로 만들어둔 계정 -> 로그인 버튼을 누르면 멤버객체 하나 만들어짐. 이거 그냥 하드코딩으로 멤버를 넣어야할때 사용할거.
        Member member = new Member();
        member.makeMember("id123","password123","김태훈","sad2@naver.com","010-1234-2345");
        memberService.join(member);


        return "login";
    }

    @GetMapping("/join")
    public String join(Model model) {
        return "join";
    }

    @GetMapping("/write")
    public String write_form(Model model) {
        PostForm postForm = new PostForm();
        model.addAttribute("postForm", postForm);
        return "write_form";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute PostForm postForm) {

        //로그인 구현 안되어 임시로 만들어둔 계정
        Member member = memberService.findMemberByUserId("id123");

        Post post = Post.makePost(member, postForm); // 멤버는 로그인도 안해서 구현못함.
        postService.registerPost(post);

        return "redirect:/";
    }

    @GetMapping("/content")
    public String see_content(@RequestParam Long postId, Model model) {
        Post post = postService.findPost(postId);
        CommentForm commentForm = new CommentForm();
        commentForm.setPostId(post.getId());

        model.addAttribute("post", post);
        model.addAttribute("commentForm",commentForm);
        System.out.println("post = " + post);

        List<Comment> comments = commentService.findComments(postId);
        model.addAttribute("comments",comments);

        return "see_content";
    }

    @PostMapping("/content")
    public String write_comment(@ModelAttribute CommentForm commentForm) { //postId를 보내주자. onclick
        System.out.println("commentFormPostId = " + commentForm.getPostId());
        System.out.println("commentFormCommetn = " + commentForm.getComment());

        //로그인 구현 안되어 임시로 만들어둔 계정
        Member member = memberService.findMemberByUserId("id123");

        System.out.println("member.getId() = " + member.getId());

        commentService.comment(member.getId(), commentForm.getPostId(), commentForm);
        return "redirect:/content?postId="+commentForm.getPostId();
    }
}
