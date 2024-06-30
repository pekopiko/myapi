package com.cicd.myapi.security;

import com.cicd.myapi.domain.Member;
import com.cicd.myapi.dto.MemberUserDetail;
import com.cicd.myapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("********* CustomUserDetailsService/loadUserByUsername - username : {}", username);
        Member member = memberRepository.getMemberWithRoles(username);
        if(member == null) { // 없는 사용자(email)일 경우 예외 발생
            throw new UsernameNotFoundException("Email(username) Not Found");
        }
        MemberUserDetail memberUserDetail = new MemberUserDetail(member.getEmail(),
                member.getPassword(),
                member.getNickname(),
                member.isSocial(),
                member.getRoleList().stream()
                        .map(role -> role.name())
                        .collect(Collectors.toList()));
        log.info("********* CustomUserDetailsService/loadUserByUsername - memberUserDetail : {}", memberUserDetail);
        return memberUserDetail;
    }
}
