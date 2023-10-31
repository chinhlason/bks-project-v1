package com.bksproject.bksproject.DTO;

import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String password;
    private String email;
    private String fullname;
    private String phone;
}
