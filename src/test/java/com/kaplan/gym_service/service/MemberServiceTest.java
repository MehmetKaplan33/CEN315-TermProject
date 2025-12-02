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
        Member member = new Member();
        member.setName("Test");

        when(memberRepository.save(any(Member.class))).thenReturn(member);

        memberService.createMember(member);

        verify(memberRepository).save(member);
    }

    @Test
    void shouldGetMemberById() {
        Member member = new Member();
        member.setId(1L);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        Member result = memberService.getMemberById(1L);

        assertEquals(1L, result.getId());
    }
}