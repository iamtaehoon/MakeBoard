package com.practice.board.repository;

import com.practice.board.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 회원저장() throws Exception {
        //given
        Member member = new Member();
        member.makeMember("ss","123","taehoon","kimth9981@naver.com","010-1234-1263");
        
        //when
        Long savedMemberId = memberRepository.saveMember(member);
        
        //then
        System.out.println("savedUserId = " + savedMemberId);
        Assertions.assertThat(savedMemberId).isEqualTo(member.getId());

    }


    @Test // 회원이 없는 경우는 서비스계층에서 걸러줄거.
    public void 유저아이디로_회원찾기() throws Exception {
        //given
        Member member = new Member();
        member.makeMember("ss","123","taehoon","kimth9981@naver.com","010-1234-1263");
        memberRepository.saveMember(member);

        //when
        Member memberByUserId = memberRepository.findMemberByUserId(member.getUserId()).get(0);

        //then
        Assertions.assertThat(memberByUserId).isEqualTo(member);
        
        Assertions.assertThat(memberByUserId.getId()).isEqualTo(member.getId());
        Assertions.assertThat(memberByUserId.getUserId()).isEqualTo(member.getUserId());
        Assertions.assertThat(memberByUserId.getUserName()).isEqualTo(member.getUserName());
        Assertions.assertThat(memberByUserId.getEmail()).isEqualTo(member.getEmail());
        Assertions.assertThat(memberByUserId.getPhoneNumber()).isEqualTo(member.getPhoneNumber());
        
    }

    @Test
    public void 아이디_이메일로_회원찾기() throws Exception {
        //given
        Member member1 = new Member();
        member1.makeMember("1번","123","taehoon","kimth9981@naver.com","010-1234-1263");

        Member member2 = new Member();
        member2.makeMember("2번","123","taehoon","asgasf@naver.com","010-2222-1263");

        Member member3 = new Member();
        member3.makeMember("3번","123","nottaehoon","asga2sf@naver.com","010-2222-2263");

        memberRepository.saveMember(member1);
        memberRepository.saveMember(member2);
        memberRepository.saveMember(member3);

        //when
        List<Member> findMember = memberRepository.findMemberByUserNameAndEmail("taehoon", "asgasf@naver.com");

        //then
        Assertions.assertThat(findMember.get(0)).isEqualTo(member2);
    }


}