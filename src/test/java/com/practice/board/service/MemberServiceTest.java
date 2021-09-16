package com.practice.board.service;

import com.practice.board.domain.Member;
import com.practice.board.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.makeMember("ss","123","taehoon","kimth9981@naver.com","010-1234-1263");

        //when
        Long saveMember = memberService.join(member);

        //then
        assertThat(saveMember).isEqualTo(member.getId()); //더티체킹으로 가능
    }

    @Test
    public void 로그인() throws Exception {
        //given
        Member member1 = new Member();
        member1.makeMember("1번","123","taehoon","kimth9981@naver.com","010-1234-1263");
        Long saveMember1 = memberService.join(member1);

        Member member2 = new Member();
        member2.makeMember("2번","123","taehoon","asgasf@naver.com","010-2222-1263");
        Long saveMember2 = memberService.join(member2);

        Member member3 = new Member();
        member3.makeMember("3번","123","nottaehoon","asga2sf@naver.com","010-2222-2263");
        Long saveMember3 = memberService.join(member3);

        //when
        boolean login = memberService.login(member1.getUserId(), member1.getPassword());
        //then

        assertThat(login).isEqualTo(true);
    }

    @Test
    public void 로그인_아이디없는상황() throws Exception {
        //given
        Member member1 = new Member();
        member1.makeMember("1번","123","taehoon","kimth9981@naver.com","010-1234-1263");
        Long saveMember1 = memberService.join(member1);

        Member member2 = new Member();
        member2.makeMember("2번","123","taehoon","asgasf@naver.com","010-2222-1263");
        Long saveMember2 = memberService.join(member2);

        Member member3 = new Member();
        member3.makeMember("3번","123","nottaehoon","asga2sf@naver.com","010-2222-2263");
        Long saveMember3 = memberService.join(member3);

        //when
        //then
        org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class,()->{
            memberService.login("dasdada@ㅇㅈㅂㄴㅂ1", member1.getPassword());
        });
    }

    @Test
    public void 로그인_비밀번호틀린상황() throws Exception {
        //given
        Member member1 = new Member();
        member1.makeMember("1번","123","taehoon","kimth9981@naver.com","010-1234-1263");
        Long saveMember1 = memberService.join(member1);

        Member member2 = new Member();
        member2.makeMember("2번","123","taehoon","asgasf@naver.com","010-2222-1263");
        Long saveMember2 = memberService.join(member2);

        Member member3 = new Member();
        member3.makeMember("3번","123","nottaehoon","asga2sf@naver.com","010-2222-2263");
        Long saveMember3 = memberService.join(member3);

        //when
        boolean login = memberService.login(member1.getUserId(), "asdiha124klsjad");

        //then
        assertThat(login).isEqualTo(false);
    }


    @Test // 사람들은 다 controller에 이메일 배치해뒀네, 그리고 내가 만든거 보내지지 않음. 일단 나중에 해결하자.
    public void 아이디찾기_이메일로보내기() throws Exception {
        //given
        Member member1 = new Member();
        member1.makeMember("1번","123","taehoon","kimth9981@naver.com","010-1234-1263");
        Long saveMember1 = memberService.join(member1);

        Member member2 = new Member();
        member2.makeMember("2번","123","taehoon","asgasf@naver.com","010-2222-1263");
        Long saveMember2 = memberService.join(member2);

        Member member3 = new Member();
        member3.makeMember("3번","123","nottaehoon","asga2sf@naver.com","010-2222-2263");
        Long saveMember3 = memberService.join(member3);

        //when
        memberService.sendMessgeByUserNameAndEmail(member1.getUserName(),member1.getEmail());

        //then
        //내가 직접 이메일 들어가서 확인하기 ㅋㅋ
    }
}