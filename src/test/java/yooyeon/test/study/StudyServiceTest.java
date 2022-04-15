package yooyeon.test.study;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import yooyeon.test.member.Member;
import yooyeon.test.member.MemberService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    // 방법 1: Mockito.mock()
    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @Test
    void createStudyService() {
        MemberService memberService = mock(MemberService.class);
        StudyRepository studyRepository = mock(StudyRepository.class);

        StudyService studyService = new StudyService(memberService, studyRepository);

        assertNotNull(studyService);
    }

    // 방법 2 @Mock 어노테이션, 매개변수
    @Test
    void createStudyService2(@Mock MemberService memberService,
                             @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }

    @Test
    void stubbing(@Mock MemberService memberService,
                  @Mock StudyRepository studyRepository) {
        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@gmail.com");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));

        Study study = new Study(10, "java");
        assertEquals("test@gmail.com", memberService.findById(1L).get().getEmail());
    }
    @Test
    void stubbing1(@Mock MemberService memberService) {
        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@gmail.com");

        when(memberService.findById(any())).thenReturn(Optional.of(member)); // 매개변수 값이 상관 없을 때
        assertEquals("test@gmail.com", memberService.findById(1L).get().getEmail());
        assertEquals("test@gmail.com", memberService.findById(2L).get().getEmail());
    }

    @Test
    void stubbing2(@Mock MemberService memberService) {

        // validate(1L) 호출 시 IllegalArgumentException() 발생하도록
        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

        assertThrows(IllegalArgumentException.class, () -> {
            memberService.validate(1L);
        });

        memberService.validate(2L);
    }
    @Test
    void stubbing3(@Mock MemberService memberService) {
        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@gmail.com");
        when(memberService.findById(any()))
                .thenReturn(Optional.of(member)) //1
                .thenThrow(new RuntimeException()) //2
                .thenReturn(Optional.empty()); //3
        //1
        Optional<Member> byId = memberService.findById(1L);
        assertEquals("test@gmail.com", byId.get().getEmail());

        //2
        assertThrows(RuntimeException.class, () ->
                memberService.findById(2L));

        //3
        assertEquals(Optional.empty(), memberService.findById(3L));
    }

    @Test
    void stubbing4(@Mock MemberService memberService,
                   @Mock StudyRepository studyRepository) {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@gmail.com");

        Study study = new Study(10, "java");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);
        assertEquals(member, study.getOwner());

        verify(memberService,times(1)).notify(study);
        verify(memberService,times(1)).notify(member);
        verify(memberService, never()).validate(any());

        InOrder inOrder = inOrder(memberService);

        // 순서 검증
       inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);

    }
    @Test
    void stubbing5(@Mock MemberService memberService,
                   @Mock StudyRepository studyRepository) {
        //Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@gmail.com");

        Study study = new Study(10, "java");

        // BDD
        given(memberService.findById(1L)).willReturn(Optional.of(member));
        given(studyRepository.save(study)).willReturn(study);

        // When
        studyService.createNewStudy(1L, study);

        // Then
        assertEquals(member, study.getOwner());
        then(memberService).should(times(1)).notify(study);
        //then(memberService).shouldHaveNoMoreInteractions();
    }

}