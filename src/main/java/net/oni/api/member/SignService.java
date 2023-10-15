package net.oni.api.member;

import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.oni.api.member.dto.SignRequest;
import net.oni.api.member.dto.SignResponse;
import net.oni.api.security.JwtProvider;

@Service
@Transactional
@RequiredArgsConstructor
public class SignService {
    
    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    public SignResponse login(SignRequest request) throws Exception {
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BadCredentialsException("잘못된 계정정보입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("잘못된 계정정보입니다.");
        }

        return SignResponse.builder()
                .id(member.getId())
                .account(member.getAccount())
                .name(member.getName())
                .email(member.getEmail())
                .roles(member.getRoles())
                .token(jwtProvider.createToken(member.getEmail(), member.getRoles()))
                .build();
    }

    public boolean register(SignRequest request) throws Exception {
        try {
            Member member = Member.builder()
                    .account(request.getAccount())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .name(request.getName())
                    .email(request.getEmail())
                    .build();

            member.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));

            memberRepository.save(member);

        } catch(Exception e) {
            e.printStackTrace();
            throw new Exception("잘못된 요청입니다.");
        }

        return true;
    }

    public SignResponse getMember(String email) throws Exception {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));
        return new SignResponse(member);
    }
}
