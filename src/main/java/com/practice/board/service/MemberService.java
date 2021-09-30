package com.practice.board.service;

import com.practice.board.domain.Member;
import com.practice.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String form;

    /**
     * 회원가입
     */
    public Long join(Member member) {
        validateSameUserName(member);
        return memberRepository.saveMember(member); //여기서 보기가 좀 불편하네. saveMember 했는데 뭐가 나올지 감이 안잡힘. 그래서 repository에서 void 리턴했나?
    }

    /**
     * 로그인
     */
    public boolean login(String userId, String password) { //로그인은 공부를 할 필요가 있겠다. -> boolean 타입이 맞는지?
        Member member = findMemberByUserId(userId);
        if (password.equals(member.getPassword())) {//입력한 비밀번호와 입력한아이디의비밀번호가 같으면
            return true;
        }
        return false;
    }


    /**
     * member 찾기로 -> userName과 이메일로
     */
    public String findUserIdByUserNameandEmail(String userName, String email) {
        List<Member> membersWithSameNameAndEmail = memberRepository.findMemberByUserNameAndEmail(userName, email);//이메일에 unique 걸어둬야겠군 userId랑 같이 중복확인도 만들어주고
        if (membersWithSameNameAndEmail.isEmpty()){
            //해당 아이디는 존재하지 않습니다.
            throw new IllegalStateException("해당 아이디는 존재하지 않습니다.");
        }
        Member member = membersWithSameNameAndEmail.get(0);

        return member.getUserId();
    }

    /**
     * member 찾기 -> userId
     */
    public Member findMemberByUserId(String userId) {
        List<Member> memberByUserId = memberRepository.findMemberByUserId(userId);

        if (memberByUserId.isEmpty()){
            //해당 아이디는 존재하지 않습니다.
            //에러 던지기
            throw new IllegalStateException("해당 아이디는 존재하지 않습니다.");
        }
        Member member = memberByUserId.get(0);
        return member;
    }

    /**
     * member 찾기 -> Long타입 id로
     */
    public Member findMemberById(Long id) {
        return memberRepository.findMemberById(id);
    }

    /**
     * 아이디 찾아서 이메일로 보내주기
     * 아직 미구현. 컨트롤러로 옮겨야함. 그러면 서비스에는 뭘 만들어둬야할까?
     */
    public void sendMessgeByUserNameAndEmail(String userName, String email) throws MessagingException {
        //간단하게 하기 위해 이메일로 -> 이메일 / 전화번호 둘 중 하나를 받을 수 있게 하자. shape보고 내가 나눠주면 댈듯
        String userId = findUserIdByUserNameandEmail(userName, email);
        sendMessageAboutUserId(email, userId);

    }


    /**
     * 비밀번호 변경
     * 이메일 인증코드 받아오고 하는 절차가 복잡할거같으니 일단 keep. 다음에 만들기.
     */


    public void sendMessageAboutUserId(String email, String userId) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setFrom(form);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("[태훈Board] 해당 이메일로 가입된 Board의 아이디");
        mimeMessageHelper.setText("태훈Board에 가입하신 아이디는 " + userId + " 입니다.\n"); // 나중에 thymeleaf 공부하고 모양새 바꾸기
    }


    private void validateSameUserName(Member member) {
        List<Member> memberByUserId = memberRepository.findMemberByUserId(member.getUserId());
        if (!memberByUserId.isEmpty()) { //크기가 0이 아니면 //해당 이름으로 찾았는데 -> 같은 이름을 가진게 DB에 있음.
            throw new IllegalStateException();//어떤 에러문을 던져야하는거지?
        }
    }


}
