package com.practice.board.controller;

import com.practice.board.domain.Member;
import com.practice.board.domain.Post;
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

    @GetMapping("/")
    public String mainPage(Model model) {

        System.out.println("mainPage 들어옴.");
        List<Post> posts = postService.findPosts();
        model.addAttribute("posts", posts);

        return "board";
    }

    @GetMapping("/login")
    public String login(Model model) {
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
        Member member = new Member();
//        member.makeMember("taehoon123","xogns123","김태훈","asd@naver.com","010-2222-2222");
        Post post = Post.makePost(null, postForm); // 멤버는 로그인도 안해서 구현못함.
        postService.registerPost(post);

        return "redirect:/";
    }

    @GetMapping("/content")
    public String see_content(@RequestParam Long postId, Model model) {
        Post post = postService.findPost(postId);
        model.addAttribute("post", post);
        System.out.println("post = " + post);
        return "see_content";
    }
}
