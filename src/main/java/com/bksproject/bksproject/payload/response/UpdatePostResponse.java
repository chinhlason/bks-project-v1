package com.bksproject.bksproject.payload.response;


import com.bksproject.bksproject.Model.Users;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Data
@Setter
@Getter
public class UpdatePostResponse {
    private String message;
    private Instant updateAt;
    private String userUpdate;

    public UpdatePostResponse(String message, String userUpdate) {
        this.message = message;
        this.updateAt = Instant.now();
        this.userUpdate = userUpdate;
    }
}
