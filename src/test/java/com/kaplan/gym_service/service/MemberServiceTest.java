package com.kaplan.gym_service.service;

import com.kaplan.gym_service.model.Member;
import com.kaplan.gym_service.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    void shouldCreateMember() {
        // GIVEN
        Member member = new Member();
        member.setName("Test Member");

        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // WHEN
        Member result = memberService.createMember(member);

        // THEN
        assertNotNull(result);
        assertEquals("Test Member", result.getName());

        verify(memberRepository).save(member);
    }

    @Test
    void shouldGetMemberById() {
        // GIVEN
        Member member = new Member();
        member.setId(1L);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        // WHEN
        Member result = memberService.getMemberById(1L);

        // THEN
        assertEquals(1L, result.getId());
        verify(memberRepository).findById(1L);
    }

    @Test
    void shouldGetAllMembers() {
        // WHEN
        memberService.getAllMembers();

        // THEN
        verify(memberRepository).findAll();
    }
}