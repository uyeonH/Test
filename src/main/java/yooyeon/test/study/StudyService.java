package yooyeon.test.study;

import yooyeon.test.member.Member;
import yooyeon.test.member.MemberService;

import java.util.Optional;

public class StudyService {
    private final MemberService memberService;
    private final StudyRepository studyRepository;

    public StudyService(MemberService memberService, StudyRepository repository) {
        assert memberService != null;
        assert repository != null;
        this.memberService = memberService;
        this.studyRepository = repository;
    }

    public Study createNewStudy(Long memberId, Study study) {
        Optional<Member> member = memberService.findById(memberId);
        study.setOwner(member.orElseThrow(() -> new IllegalArgumentException("Member dosen't exist for id: '" + memberId + "'")));

        Study newStudy = studyRepository.save(study);
        memberService.notify(study);
        memberService.notify(member.get());
        return newStudy;
    }
}
