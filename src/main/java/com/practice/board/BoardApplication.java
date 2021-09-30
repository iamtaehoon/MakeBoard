package com.practice.board;

import com.practice.board.domain.Member;
import com.practice.board.repository.MemberRepository;
import com.practice.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BoardApplication {

	MemberService memberService;

	public static void main(String[] args) {

		SpringApplication.run(BoardApplication.class, args);

	}

}
