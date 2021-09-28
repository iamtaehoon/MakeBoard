package com.practice.board.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class BoardController {

    @GetMapping("/")
    public String mainPage(Model model) {
        //model.addAttribute
        return "board";
    }
}
