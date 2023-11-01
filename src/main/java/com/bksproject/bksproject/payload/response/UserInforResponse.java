package com.bksproject.bksproject.payload.response;

import com.bksproject.bksproject.Enum.User_roles;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
public class UserInforResponse {
    private Long id;
    private String username;
    private String fullname;
    private String phone;
    private String email;
    private List<User_roles> role;
}
