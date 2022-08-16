package api.v1.member.service;

import api.v1.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MemberService {
    public Member createMember(Member member){
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Member updateMember(Member member){
        return null;
    }

    @Transactional(readOnly = true)
    public Member findMember(long memberId){return null;}

    public Page<Member> findMembers(int page, int size){return null;}

    public void deleteMember(long memberId){}
}
