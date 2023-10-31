package com.bksproject.bksproject.payload.response;

import com.bksproject.bksproject.Model.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@Setter
@Getter
public class LoginResponse {
    private String token;
    private String type = "Bearer";
//    private String refreshToken;
    private Long id;
    private String username;
    private String fullname;
    private String phone;
    private String email;
    private Collection<? extends GrantedAuthority> roles;
    public LoginResponse(String accessToken, Long id, String username, String email, String fullname, String phone, Collection<? extends GrantedAuthority> roles) {
        this.token = accessToken;
//        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.fullname = fullname;
        this.roles = roles;
    }
}
