package com.bksproject.bksproject.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UpdateUserRequest {
    @Email
    @Size(max = 50)
    private String email;

    private String fullname;

    private String phone;
}
