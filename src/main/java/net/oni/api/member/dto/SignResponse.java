package net.oni.api.member.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.oni.api.member.Authority;
import net.oni.api.member.Member;

@Getter
@Builder @AllArgsConstructor @NoArgsConstructor
public class SignResponse {
    
    private Long id;

    private String account;

    private String name;

    private String email;

    private List<Authority> roles; // = new ArrayList<>();

    private String token;

    public SignResponse(Member member) {
        this.id = member.getId();
        this.account = member.getAccount();
        this.name = member.getName();
        this.email = member.getEmail();
        this.roles = member.getRoles();
    }
}
