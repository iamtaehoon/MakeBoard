package com.practice.board.repository;

import com.practice.board.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em; // required 있으면 자동으로 연결되겠지? @Persis~~. 안넣어도 되겟지

    /**
     * 회원 저장
     */
    public Long saveMember(Member member) {
        em.persist(member);
        return member.getId();
    }

    /**
     * 멤버 1명 찾기
     * @param memberId : 멤버의 기본키
     * @return 해당 멤버객체 반환
     */
    public Member findMember(Long memberId) {
        return em.find(Member.class, memberId);
    }

    public List<Member> findMemberByUserId(String userId) {
        //userId라는 기본키 아닌 값으로 Member를 찾아내야함. -> jpql이 들어갈 떄.
        return em.createQuery("select m from Member m " +
                    "where m.userId = :userId", Member.class)
                    .setParameter("userId", userId)
                    .getResultList();
    }

// 강사님 비슷한 코드는 이 부분이 서비스 계층으로 올라감. 레포지토리에서는 이름으로 찾는 쿼리 정도만 들어감.
//    public Member findMemberByUserId(String userId) {
//        //userId라는 기본키 아닌 값으로 Member를 찾아내야함. -> jpql이 들어갈 떄.
//
//        //찾는 아이디가 없을 경우 에러가 뜸. 해당 에러가 떴을 경우 "해당되는 아이디가 없습니다." 를 띄워줘야함.
//        try{
//            Member member = em.createQuery("select m from Member m " +
//                    "where m.userId = :userId", Member.class)
//                    .setParameter("userId", userId)
//                    .getSingleResult();
//            return member;
//        } catch (NoResultException e){
//
//        } catch (Exception e){
//            //에러상황. 이런 경우는 없음.
//        }
//    }

}
