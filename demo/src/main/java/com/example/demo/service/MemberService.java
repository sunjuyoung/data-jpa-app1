package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.mapper.MemberMapper;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member){
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberMapper.findByEmail(email);
        //Member member = memberRepository.findByEmail(email);
        if(member == null){
            throw new UsernameNotFoundException(email);
        }

        UserDetails userDetails = User.builder().username(member.getNickname())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();

        return userDetails;
    }
}
