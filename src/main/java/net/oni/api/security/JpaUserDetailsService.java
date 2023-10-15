package net.oni.api.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.oni.api.member.Member;
import net.oni.api.member.MemberRepository;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(" Authentication Fail!"));
        return new CustomUserDetails(member);
    }
}
