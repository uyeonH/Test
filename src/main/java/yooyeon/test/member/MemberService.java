package yooyeon.test.member;

import yooyeon.test.exception.MemberNotFoundException;
import yooyeon.test.study.Study;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId) throws MemberNotFoundException;

    void validate(Long memberId);

    void notify(Study newStudy);

    void notify(Member member);
}
