package com.testcar.car.domains.member;

import static com.testcar.car.common.Constant.MEMBER_EMAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.testcar.car.common.MemberEntityFactory;
import com.testcar.car.common.exception.BadRequestException;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.department.DepartmentService;
import com.testcar.car.domains.department.entity.Department;
import com.testcar.car.domains.member.entity.Member;
import com.testcar.car.domains.member.model.RegisterMemberRequest;
import com.testcar.car.domains.member.model.UpdateMemberRequest;
import com.testcar.car.domains.member.model.vo.MemberFilterCondition;
import com.testcar.car.domains.member.repository.MemberRepository;
import com.testcar.car.domains.member.request.MemberRequestFactory;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @Mock private DepartmentService departmentService;
    @Mock private MemberRepository memberRepository;
    @Mock private static Member mockMember;
    @InjectMocks private MemberService memberService;

    private static Member member;
    private static Department department;
    private static Department anotherDepartment;
    private static final Long memberId = 1L;
    private static final Long anotherMemberId = 2L;

    @BeforeAll
    public static void setUp() {
        member = MemberEntityFactory.createMember();
        department = MemberEntityFactory.createDepartment();
        anotherDepartment = MemberEntityFactory.createDepartment();
    }

    @Test
    void 사용자ID로_DB에서_사용자_엔티티를_가져온다() {
        // given
        when(memberRepository.findByIdAndDeletedFalse(memberId)).thenReturn(Optional.of(member));

        // when
        final Member result = memberService.findById(memberId);

        // then
        assertEquals(member, result);
        verify(memberRepository).findByIdAndDeletedFalse(memberId);
    }

    @Test
    void 해당_ID의_사용자가_DB에_존재하지_않으면_오류가_발생한다() {
        // given
        when(memberRepository.findByIdAndDeletedFalse(memberId)).thenReturn(Optional.empty());

        // when, then
        Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    memberService.findById(memberId);
                });
    }

    @Test
    void 조건에_맞는_사용자_페이지를_필터링하여_가져온다() {
        // given
        MemberFilterCondition condition = new MemberFilterCondition();
        Pageable pageable = mock(Pageable.class);
        Page<Member> mockPage = mock(Page.class);
        when(memberRepository.findAllPageByCondition(condition, pageable)).thenReturn(mockPage);

        // when
        final Page<Member> result = memberService.findAllPageByCondition(condition, pageable);

        // then
        assertEquals(mockPage, result);
        verify(memberRepository).findAllPageByCondition(condition, pageable);
    }

    @Test
    void 새로운_사용자를_등록한다() {
        // given
        final RegisterMemberRequest request = MemberRequestFactory.createMemberRequest();
        when(departmentService.findById(request.getDepartmentId())).thenReturn(department);
        given(memberRepository.save(any(Member.class))).willReturn(member);

        // when
        final Member newMember = memberService.register(request);

        // then
        assertNotNull(newMember);
        assertEquals(member, newMember);
        then(departmentService).should().findById(any(Long.class));
        then(memberRepository).should().save(any(Member.class));
    }

    @Test
    void DB에_이미_존재하는_이메일로는_등록할수_없다() {
        // given
        final RegisterMemberRequest request = MemberRequestFactory.createMemberRequest();
        when(memberRepository.existsByEmailAndDeletedFalse(request.getEmail())).thenReturn(true);

        // when, then
        Assertions.assertThrows(
                BadRequestException.class,
                () -> {
                    memberService.register(request);
                });
    }

    @Test
    void 사용자_정보를_수정한다() {
        // given
        final UpdateMemberRequest request = MemberRequestFactory.createUpdateMemberRequest();
        when(departmentService.findById(request.getDepartmentId())).thenReturn(anotherDepartment);
        when(memberRepository.findByIdAndDeletedFalse(memberId)).thenReturn(Optional.of(member));
        given(memberRepository.save(any(Member.class))).willReturn(member);

        // when
        final Member newMember = memberService.updateById(memberId, request);

        // then
        assertNotNull(newMember);
        then(memberRepository).should().findByIdAndDeletedFalse(any(Long.class));
        then(memberRepository).should().save(any(Member.class));
    }

    @Test
    void 요청_이메일과_사용자_이메일이_같다면_이메일_중복을_검증하지_않는다() {
        // given
        final UpdateMemberRequest request =
                MemberRequestFactory.createUpdateMemberRequest(MEMBER_EMAIL);
        when(departmentService.findById(request.getDepartmentId())).thenReturn(anotherDepartment);
        when(memberRepository.findByIdAndDeletedFalse(memberId)).thenReturn(Optional.of(member));
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // when
        final Member newMember = memberService.updateById(memberId, request);

        // then
        assertNotNull(newMember);
        assertEquals(member.getEmail(), newMember.getEmail());
        then(memberRepository).should().findByIdAndDeletedFalse(any(Long.class));
        then(memberRepository).should().save(any(Member.class));
    }

    @Test
    void DB에_이미_존재하는_이메일로는_변경할수_없다() {
        // given
        final UpdateMemberRequest request = MemberRequestFactory.createInvalidUpdateMemberRequest();
        when(memberRepository.findByIdAndDeletedFalse(memberId)).thenReturn(Optional.of(member));
        when(departmentService.findById(request.getDepartmentId())).thenReturn(anotherDepartment);
        when(memberRepository.existsByEmailAndDeletedFalse(request.getEmail())).thenReturn(true);

        // when, then
        Assertions.assertThrows(
                BadRequestException.class,
                () -> {
                    memberService.updateById(memberId, request);
                });
    }

    @Test
    void 사용자를_삭제한다() {
        // given
        when(memberRepository.findByIdAndDeletedFalse(memberId)).thenReturn(Optional.of(member));
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // when
        final Member deletedMember = memberService.deleteById(member, memberId);

        // then
        assertTrue(deletedMember.getDeleted());
        verify(memberRepository).findByIdAndDeletedFalse(memberId);
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    void 자기_자신은_삭제할수_없다() {
        // given
        when(mockMember.getId()).thenReturn(1L);

        // when
        Assertions.assertThrows(
                BadRequestException.class,
                () -> {
                    memberService.deleteById(mockMember, memberId);
                });
        then(memberRepository).shouldHaveNoMoreInteractions();
    }
}
