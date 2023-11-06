package com.bksproject.bksproject.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String fullname;
    private String phone;
    private String email;
}
